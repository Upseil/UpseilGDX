package com.upseil.gdx.testbed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import com.upseil.gdx.lzstring.LZString;

public class LZStringTestbed {
    
    public static void main(String[] args) {
        InputStream dataStream = LZStringTestbed.class.getResourceAsStream("/com/upseil/gdx/test/resources/lzstring-compressed-large.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream))) {
            String compressed = reader.lines().collect(Collectors.joining());
            String decompressed = LZString.decompressFromBase64(compressed);
            
            
            int compressedLength = compressed.length();
            int decompressedLength = decompressed.length();
            double compressionRate = (float) compressedLength / decompressedLength;
            System.out.println(String.format("Decompressed %d characters -> Compressed %d characters (%.4f%%)\n%s",
                                             decompressedLength, compressedLength, compressionRate, decompressed));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
