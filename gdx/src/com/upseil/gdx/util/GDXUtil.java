package com.upseil.gdx.util;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public final class GDXUtil {
    
    public static JsonValue readJson(String path) {
        return readJson(Gdx.files.internal(path));
    }
    
    public static JsonValue readJson(FileHandle file) {
        JsonValue root;
        try (InputStream jsonStream = file.read()) {
            JsonReader reader = new JsonReader();
            root = reader.parse(jsonStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not read json: " + file.path(), e);
        }
        return root;
    }
    
    private GDXUtil() { }
    
}
