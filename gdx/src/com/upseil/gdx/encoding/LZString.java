/*
 * LZString4Java By Rufus Huang 
 * https://github.com/rufushuang/lz-string4java
 * 
 * MIT License
 * 
 * Copyright (c) 2016 rufushuang
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * 
 * Port from original JavaScript version by pieroxy 
 * https://github.com/pieroxy/lz-string
 * 
 * Changes done by Lennart Hensler:
 *  - Removed methods except for compression and decompression to Base64
 *  - Replaced the TODOs with Comment
 *  - Replaced the collections with libGDX collections
 *  - Reduced the amount of auto-boxing
 *  - Removed the unused variable
 *  - Applied some improvements described in https://github.com/rufushuang/lz-string4java/pull/7
 */

package com.upseil.gdx.encoding;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;

public class LZString {

    private static final char[] keyStrBase64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static final ObjectMap<char[], IntIntMap> baseReverseDic = new ObjectMap<char[], IntIntMap>();
    
    private static final CompressFunctionWrapper base64CompressFunctionWrapper = new CompressFunctionWrapper() {
        @Override
        public char doFunc(int a) {
            return keyStrBase64[a];
        }
    };
    
    public static String compressToBase64(String input) {
        if (input == null)
            return "";
        String res = LZString._compress(input, 6, base64CompressFunctionWrapper);
        switch (res.length() % 4) { // To produce valid Base64
        default: // When could this happen ?
        case 0:
            return res;
        case 1:
            return res + "===";
        case 2:
            return res + "==";
        case 3:
            return res + "=";
        }
    }
    
    public static String decompressFromBase64(String inputStr) {
        if (inputStr == null)
            return "";
        if (inputStr.isEmpty())
            return null;
        return LZString._decompress(inputStr.length(), 32, new DecompressFunctionWrapper() {
            @Override
            public char doFunc(int index) {
                return getBaseValue(keyStrBase64, inputStr.charAt(index));
            }
        });
    }

    private static char getBaseValue(char[] alphabet, char character) {
        IntIntMap map = baseReverseDic.get(alphabet);
        if (map == null) {
            map = new IntIntMap();
            baseReverseDic.put(alphabet, map);
            for (int i = 0; i < alphabet.length; i++) {
                map.put(alphabet[i], i);
            }
        }
        return (char) map.get(character, -1);
    }
    
    private static abstract class CompressFunctionWrapper {
        public abstract char doFunc(int i);
    }
    
    private static final StringBuilder context_data = new StringBuilder();
    
    private static String _compress(String uncompressedStr, int bitsPerChar, CompressFunctionWrapper getCharFromInt) {
        if (uncompressedStr == null) return "";
        int i, value;
        ObjectIntMap<String> context_dictionary = new ObjectIntMap<String>();
        ObjectSet<String> context_dictionaryToCreate = new ObjectSet<String>();
        String context_c = "";
        String context_wc = "";
        String context_w = "";
        int context_enlargeIn = 2; // Compensate for the first entry which should not count
        int context_dictSize = 3;
        int context_numBits = 2;
        context_data.setLength(0);
        context_data.ensureCapacity(uncompressedStr.length() / 3);
        int context_data_val = 0;
        int context_data_position = 0;
        int ii;
        
        for (ii = 0; ii < uncompressedStr.length(); ii += 1) {
            context_c = String.valueOf(uncompressedStr.charAt(ii));
            if (!context_dictionary.containsKey(context_c)) {
                context_dictionary.put(context_c, context_dictSize++);
                context_dictionaryToCreate.add(context_c);
            }

            context_wc = context_w + context_c;
            if (context_dictionary.containsKey(context_wc)) {
                context_w = context_wc;
            } else {
                if (context_dictionaryToCreate.contains(context_w)) {
                    if (context_w.charAt(0) < 256) {
                        for (i = 0; i < context_numBits; i++) {
                            context_data_val = (context_data_val << 1);
                            if (context_data_position == bitsPerChar - 1) {
                                context_data_position = 0;
                                context_data.append(getCharFromInt.doFunc(context_data_val));
                                context_data_val = 0;
                            } else {
                                context_data_position++;
                            }
                        }
                        value = context_w.charAt(0);
                        for (i = 0; i < 8; i++) {
                            context_data_val = (context_data_val << 1) | (value & 1);
                            if (context_data_position == bitsPerChar - 1) {
                                context_data_position = 0;
                                context_data.append(getCharFromInt.doFunc(context_data_val));
                                context_data_val = 0;
                            } else {
                                context_data_position++;
                            }
                            value = value >> 1;
                        }
                    } else {
                        value = 1;
                        for (i = 0; i < context_numBits; i++) {
                            context_data_val = (context_data_val << 1) | value;
                            if (context_data_position == bitsPerChar - 1) {
                                context_data_position = 0;
                                context_data.append(getCharFromInt.doFunc(context_data_val));
                                context_data_val = 0;
                            } else {
                                context_data_position++;
                            }
                            value = 0;
                        }
                        value = context_w.charAt(0);
                        for (i = 0; i < 16; i++) {
                            context_data_val = (context_data_val << 1) | (value & 1);
                            if (context_data_position == bitsPerChar - 1) {
                                context_data_position = 0;
                                context_data.append(getCharFromInt.doFunc(context_data_val));
                                context_data_val = 0;
                            } else {
                                context_data_position++;
                            }
                            value = value >> 1;
                        }
                    }
                    context_enlargeIn--;
                    if (context_enlargeIn == 0) {
                        context_enlargeIn = powerOf2(context_numBits);
                        context_numBits++;
                    }
                    context_dictionaryToCreate.remove(context_w);
                } else {
                    value = context_dictionary.get(context_w, -1);
                    for (i = 0; i < context_numBits; i++) {
                        context_data_val = (context_data_val << 1) | (value & 1);
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0;
                            context_data.append(getCharFromInt.doFunc(context_data_val));
                            context_data_val = 0;
                        } else {
                            context_data_position++;
                        }
                        value = value >> 1;
                    }

                }
                context_enlargeIn--;
                if (context_enlargeIn == 0) {
                    context_enlargeIn = powerOf2(context_numBits);
                    context_numBits++;
                }
                // Add wc to the dictionary.
                context_dictionary.put(context_wc, context_dictSize++);
                context_w = context_c;
            }
        }
        
        // Output the code for w.
        if (!context_w.isEmpty()) {
            if (context_dictionaryToCreate.contains(context_w)) {
                if (context_w.charAt(0) < 256) {
                    for (i = 0; i < context_numBits; i++) {
                        context_data_val = (context_data_val << 1);
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0;
                            context_data.append(getCharFromInt.doFunc(context_data_val));
                            context_data_val = 0;
                        } else {
                            context_data_position++;
                        }
                    }
                    value = context_w.charAt(0);
                    for (i = 0; i < 8; i++) {
                        context_data_val = (context_data_val << 1) | (value & 1);
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0;
                            context_data.append(getCharFromInt.doFunc(context_data_val));
                            context_data_val = 0;
                        } else {
                            context_data_position++;
                        }
                        value = value >> 1;
                    }
                } else {
                    value = 1;
                    for (i = 0; i < context_numBits; i++) {
                        context_data_val = (context_data_val << 1) | value;
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0;
                            context_data.append(getCharFromInt.doFunc(context_data_val));
                            context_data_val = 0;
                        } else {
                            context_data_position++;
                        }
                        value = 0;
                    }
                    value = context_w.charAt(0);
                    for (i = 0; i < 16; i++) {
                        context_data_val = (context_data_val << 1) | (value & 1);
                        if (context_data_position == bitsPerChar - 1) {
                            context_data_position = 0;
                            context_data.append(getCharFromInt.doFunc(context_data_val));
                            context_data_val = 0;
                        } else {
                            context_data_position++;
                        }
                        value = value >> 1;
                    }
                }
                context_enlargeIn--;
                if (context_enlargeIn == 0) {
                    context_enlargeIn = powerOf2(context_numBits);
                    context_numBits++;
                }
                context_dictionaryToCreate.remove(context_w);
            } else {
                value = context_dictionary.get(context_w, -1);
                for (i = 0; i < context_numBits; i++) {
                    context_data_val = (context_data_val << 1) | (value & 1);
                    if (context_data_position == bitsPerChar - 1) {
                        context_data_position = 0;
                        context_data.append(getCharFromInt.doFunc(context_data_val));
                        context_data_val = 0;
                    } else {
                        context_data_position++;
                    }
                    value = value >> 1;
                }

            }
            context_enlargeIn--;
            if (context_enlargeIn == 0) {
                context_enlargeIn = powerOf2(context_numBits);
                context_numBits++;
            }
        }

        // Mark the end of the stream
        value = 2;
        for (i = 0; i < context_numBits; i++) {
            context_data_val = (context_data_val << 1) | (value & 1);
            if (context_data_position == bitsPerChar - 1) {
                context_data_position = 0;
                context_data.append(getCharFromInt.doFunc(context_data_val));
                context_data_val = 0;
            } else {
                context_data_position++;
            }
            value = value >> 1;
        }

        // Flush the last char
        while (true) {
            context_data_val = (context_data_val << 1);
            if (context_data_position == bitsPerChar - 1) {
                context_data.append(getCharFromInt.doFunc(context_data_val));
                break;
            }
            else
                context_data_position++;
        }

        return context_data.toString();
    }
    
    private static abstract class DecompressFunctionWrapper {
        public abstract char doFunc(int i);
    }
    
    private static class DecData {
        public char val;
        public int position;
        public int index;       
    }
    
    private static final StringBuilder result = new StringBuilder();

    private static String _decompress(int length, int resetValue, DecompressFunctionWrapper getNextValue) {
        Array<String> dictionary = new Array<String>();
        int enlargeIn = 4;
        int dictSize = 4;
        int numBits = 3;
        String entry = "";
        result.setLength(0);
        String w;
        int bits, resb; int maxpower, power;
        String c = null;
        DecData data = new DecData();
        data.val = getNextValue.doFunc(0);
        data.position = resetValue;
        data.index = 1;
        
        for (int i = 0; i < 3; i += 1) {
            dictionary.insert(i, f(i));
        }
        
        bits = 0;
        maxpower = powerOf2(2);
        power = 1;
        while (power != maxpower) {
            resb = data.val & data.position;
            data.position >>= 1;
            if (data.position == 0) {
                data.position = resetValue;
                data.val = getNextValue.doFunc(data.index++);
            }
            bits |= (resb > 0 ? 1 : 0) * power;
            power <<= 1;
        }
        
        switch (bits) {
          case 0:
              bits = 0;
              maxpower = powerOf2(8);
              power=1;
              while (power != maxpower) {
                resb = data.val & data.position;
                data.position >>= 1;
                if (data.position == 0) {
                  data.position = resetValue;
                  data.val = getNextValue.doFunc(data.index++);
                }
                bits |= (resb>0 ? 1 : 0) * power;
                power <<= 1;
              }
            c = f(bits);
            break;
          case 1:
              bits = 0;
              maxpower = powerOf2(16);
              power=1;
              while (power!=maxpower) {
                resb = data.val & data.position;
                data.position >>= 1;
                if (data.position == 0) {
                  data.position = resetValue;
                  data.val = getNextValue.doFunc(data.index++);
                }
                bits |= (resb>0 ? 1 : 0) * power;
                power <<= 1;
              }
            c = f(bits);
            break;
          case 2:
            return "";
        }
        dictionary.insert(3, c);
        w = c;
        result.append(w);
        while (true) {
            if (data.index > length) {
              return "";
            }

            bits = 0;
            maxpower = powerOf2(numBits);
            power=1;
            while (power!=maxpower) {
              resb = data.val & data.position;
              data.position >>= 1;
              if (data.position == 0) {
                data.position = resetValue;
                data.val = getNextValue.doFunc(data.index++);
              }
              bits |= (resb>0 ? 1 : 0) * power;
              power <<= 1;
            }
            // Comment: very strange here, c above is as char/string, here further is a int, rename "c" in the switch as "cc"
            int cc;
            switch (cc = bits) {
              case 0:
                bits = 0;
                maxpower = powerOf2(8);
                power=1;
                while (power!=maxpower) {
                  resb = data.val & data.position;
                  data.position >>= 1;
                  if (data.position == 0) {
                    data.position = resetValue;
                    data.val = getNextValue.doFunc(data.index++);
                  }
                  bits |= (resb>0 ? 1 : 0) * power;
                  power <<= 1;
                }

                dictionary.insert(dictSize++, f(bits));
                cc = dictSize-1;
                enlargeIn--;
                break;
              case 1:
                bits = 0;
                maxpower = powerOf2(16);
                power=1;
                while (power!=maxpower) {
                  resb = data.val & data.position;
                  data.position >>= 1;
                  if (data.position == 0) {
                    data.position = resetValue;
                    data.val = getNextValue.doFunc(data.index++);
                  }
                  bits |= (resb>0 ? 1 : 0) * power;
                  power <<= 1;
                }
                dictionary.insert(dictSize++, f(bits));
                cc = dictSize-1;
                enlargeIn--;
                break;
              case 2:
                return result.toString();
            }

            if (enlargeIn == 0) {
              enlargeIn = powerOf2(numBits);
              numBits++;
            }

            if (cc < dictionary.size && dictionary.get(cc) != null) {
                entry = dictionary.get(cc);
            } else {
              if (cc == dictSize) {
                entry = w + w.charAt(0);
              } else {
                return null;
              }
            }
            result.append(entry);

            // Add w+entry[0] to the dictionary.
            dictionary.insert(dictSize++, w + entry.charAt(0));
            enlargeIn--;

            w = entry;

            if (enlargeIn == 0) {
              enlargeIn = powerOf2(numBits);
              numBits++;
            }
        }
    }

    public static String f(int i) {
        return String.valueOf((char) i);
    }
    
    private static int powerOf2(int power) {
        return 1 << power;
    }
}