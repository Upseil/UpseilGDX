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
 *  - Replaced abstract classes with functional interfaces
 */

package com.upseil.gdx.encoding;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.upseil.gdx.util.function.IntCharFunction;

public class LZString {

    private static final char[] keyStrBase64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    private static final ObjectMap<char[], IntIntMap> baseReverseDic = new ObjectMap<char[], IntIntMap>();
    
    private static final IntCharFunction base64CompressFunction = i -> keyStrBase64[i];
    
    public static String compressToBase64(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        String res = compress(input, 6, base64CompressFunction);
        switch (res.length() % 4) {
        case 1:
            return res + "===";
        case 2:
            return res + "==";
        case 3:
            return res + "=";
        }
        return res;
    }
    
    public static String decompressFromBase64(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return decompress(input.length(), 32, i -> getBaseValue(keyStrBase64, input.charAt(i)));
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
    
    private static final StringBuilder contextData = new StringBuilder();
    
    private static String compress(String uncompressed, int bitsPerChar, IntCharFunction compress) {
        int i, value;
        ObjectIntMap<String> contextDictionary = new ObjectIntMap<String>();
        ObjectSet<String> contextDictionaryToCreate = new ObjectSet<String>();
        String contextC = "";
        String contextWC = "";
        String contextW = "";
        int contextEnlargeIn = 2; // Compensate for the first entry which should not count
        int contextDictSize = 3;
        int contextNumBits = 2;
        contextData.setLength(0);
        contextData.ensureCapacity(uncompressed.length() / 3);
        int contextDataValue = 0;
        int contextDataPosition = 0;
        int ii;
        
        for (ii = 0; ii < uncompressed.length(); ii += 1) {
            contextC = String.valueOf(uncompressed.charAt(ii));
            if (!contextDictionary.containsKey(contextC)) {
                contextDictionary.put(contextC, contextDictSize++);
                contextDictionaryToCreate.add(contextC);
            }

            contextWC = contextW + contextC;
            if (contextDictionary.containsKey(contextWC)) {
                contextW = contextWC;
            } else {
                if (contextDictionaryToCreate.contains(contextW)) {
                    if (contextW.charAt(0) < 256) {
                        for (i = 0; i < contextNumBits; i++) {
                            contextDataValue = (contextDataValue << 1);
                            if (contextDataPosition == bitsPerChar - 1) {
                                contextDataPosition = 0;
                                contextData.append(compress.apply(contextDataValue));
                                contextDataValue = 0;
                            } else {
                                contextDataPosition++;
                            }
                        }
                        value = contextW.charAt(0);
                        for (i = 0; i < 8; i++) {
                            contextDataValue = (contextDataValue << 1) | (value & 1);
                            if (contextDataPosition == bitsPerChar - 1) {
                                contextDataPosition = 0;
                                contextData.append(compress.apply(contextDataValue));
                                contextDataValue = 0;
                            } else {
                                contextDataPosition++;
                            }
                            value = value >> 1;
                        }
                    } else {
                        value = 1;
                        for (i = 0; i < contextNumBits; i++) {
                            contextDataValue = (contextDataValue << 1) | value;
                            if (contextDataPosition == bitsPerChar - 1) {
                                contextDataPosition = 0;
                                contextData.append(compress.apply(contextDataValue));
                                contextDataValue = 0;
                            } else {
                                contextDataPosition++;
                            }
                            value = 0;
                        }
                        value = contextW.charAt(0);
                        for (i = 0; i < 16; i++) {
                            contextDataValue = (contextDataValue << 1) | (value & 1);
                            if (contextDataPosition == bitsPerChar - 1) {
                                contextDataPosition = 0;
                                contextData.append(compress.apply(contextDataValue));
                                contextDataValue = 0;
                            } else {
                                contextDataPosition++;
                            }
                            value = value >> 1;
                        }
                    }
                    contextEnlargeIn--;
                    if (contextEnlargeIn == 0) {
                        contextEnlargeIn = powerOf2(contextNumBits);
                        contextNumBits++;
                    }
                    contextDictionaryToCreate.remove(contextW);
                } else {
                    value = contextDictionary.get(contextW, -1);
                    for (i = 0; i < contextNumBits; i++) {
                        contextDataValue = (contextDataValue << 1) | (value & 1);
                        if (contextDataPosition == bitsPerChar - 1) {
                            contextDataPosition = 0;
                            contextData.append(compress.apply(contextDataValue));
                            contextDataValue = 0;
                        } else {
                            contextDataPosition++;
                        }
                        value = value >> 1;
                    }

                }
                contextEnlargeIn--;
                if (contextEnlargeIn == 0) {
                    contextEnlargeIn = powerOf2(contextNumBits);
                    contextNumBits++;
                }
                // Add wc to the dictionary.
                contextDictionary.put(contextWC, contextDictSize++);
                contextW = contextC;
            }
        }
        
        // Output the code for w.
        if (!contextW.isEmpty()) {
            if (contextDictionaryToCreate.contains(contextW)) {
                if (contextW.charAt(0) < 256) {
                    for (i = 0; i < contextNumBits; i++) {
                        contextDataValue = (contextDataValue << 1);
                        if (contextDataPosition == bitsPerChar - 1) {
                            contextDataPosition = 0;
                            contextData.append(compress.apply(contextDataValue));
                            contextDataValue = 0;
                        } else {
                            contextDataPosition++;
                        }
                    }
                    value = contextW.charAt(0);
                    for (i = 0; i < 8; i++) {
                        contextDataValue = (contextDataValue << 1) | (value & 1);
                        if (contextDataPosition == bitsPerChar - 1) {
                            contextDataPosition = 0;
                            contextData.append(compress.apply(contextDataValue));
                            contextDataValue = 0;
                        } else {
                            contextDataPosition++;
                        }
                        value = value >> 1;
                    }
                } else {
                    value = 1;
                    for (i = 0; i < contextNumBits; i++) {
                        contextDataValue = (contextDataValue << 1) | value;
                        if (contextDataPosition == bitsPerChar - 1) {
                            contextDataPosition = 0;
                            contextData.append(compress.apply(contextDataValue));
                            contextDataValue = 0;
                        } else {
                            contextDataPosition++;
                        }
                        value = 0;
                    }
                    value = contextW.charAt(0);
                    for (i = 0; i < 16; i++) {
                        contextDataValue = (contextDataValue << 1) | (value & 1);
                        if (contextDataPosition == bitsPerChar - 1) {
                            contextDataPosition = 0;
                            contextData.append(compress.apply(contextDataValue));
                            contextDataValue = 0;
                        } else {
                            contextDataPosition++;
                        }
                        value = value >> 1;
                    }
                }
                contextEnlargeIn--;
                if (contextEnlargeIn == 0) {
                    contextEnlargeIn = powerOf2(contextNumBits);
                    contextNumBits++;
                }
                contextDictionaryToCreate.remove(contextW);
            } else {
                value = contextDictionary.get(contextW, -1);
                for (i = 0; i < contextNumBits; i++) {
                    contextDataValue = (contextDataValue << 1) | (value & 1);
                    if (contextDataPosition == bitsPerChar - 1) {
                        contextDataPosition = 0;
                        contextData.append(compress.apply(contextDataValue));
                        contextDataValue = 0;
                    } else {
                        contextDataPosition++;
                    }
                    value = value >> 1;
                }

            }
            contextEnlargeIn--;
            if (contextEnlargeIn == 0) {
                contextEnlargeIn = powerOf2(contextNumBits);
                contextNumBits++;
            }
        }

        // Mark the end of the stream
        value = 2;
        for (i = 0; i < contextNumBits; i++) {
            contextDataValue = (contextDataValue << 1) | (value & 1);
            if (contextDataPosition == bitsPerChar - 1) {
                contextDataPosition = 0;
                contextData.append(compress.apply(contextDataValue));
                contextDataValue = 0;
            } else {
                contextDataPosition++;
            }
            value = value >> 1;
        }

        // Flush the last char
        while (true) {
            contextDataValue = (contextDataValue << 1);
            if (contextDataPosition == bitsPerChar - 1) {
                contextData.append(compress.apply(contextDataValue));
                break;
            }
            else
                contextDataPosition++;
        }

        return contextData.toString();
    }
    
    private static class DecompressData {
        public char val;
        public int position;
        public int index;       
    }
    
    private static final StringBuilder result = new StringBuilder();

    private static String decompress(int length, int resetValue, IntCharFunction decompress) {
        Array<String> dictionary = new Array<String>();
        int enlargeIn = 4;
        int dictSize = 4;
        int numBits = 3;
        String entry = "";
        result.setLength(0);
        String w;
        String c = null;
        DecompressData data = new DecompressData();
        data.val = decompress.apply(0);
        data.position = resetValue;
        data.index = 1;
        
        for (int i = 0; i < 3; i += 1) {
            dictionary.insert(i, f(i));
        }
        
        int bits = 0;
        int resb = 0;
        int maxpower = powerOf2(2);
        int power = 1;
        while (power != maxpower) {
            resb = data.val & data.position;
            data.position >>= 1;
            if (data.position == 0) {
                data.position = resetValue;
                data.val = decompress.apply(data.index++);
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
                  data.val = decompress.apply(data.index++);
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
                  data.val = decompress.apply(data.index++);
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
                data.val = decompress.apply(data.index++);
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
                    data.val = decompress.apply(data.index++);
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
                    data.val = decompress.apply(data.index++);
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

    private static String f(int i) {
        return String.valueOf((char) i);
    }
    
    private static int powerOf2(int power) {
        return 1 << power;
    }
}