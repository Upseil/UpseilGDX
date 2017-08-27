package com.upseil.gdx.config;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.upseil.gdx.util.DoubleBasedExpression;

/**
 * Allows loading a json-based config into memory and access its values. No key
 * in the json document should contain a dot as it's used for accessing nested
 * values.
 * 
 * Example Json Document:
 * 
 * { 
 *     "room": { 
 *         "floorBase": 1.01, 
 *         "roomBase": 1.025, 
 *         "minSize": 7, 
 *         "maxSize": 100, 
 *         "sizeVariance": 1.4 
 *     } 
 * }
 * 
 * The values can now be accessed like this:
 * 
 * int minRoomSize = config.getInt("room.minSize");
 * double floorBase = config.getDouble("room.floorBase");
 * 
 * @author frederik
 *        
 */
public class RawConfig {
    
    public static RawConfig loadConfig(String path) {
        return new RawConfig(null, loadJson(path));
    }
    
    public static JsonValue loadJson(String path) {
        FileHandle handle = Gdx.files.internal(path);
        JsonValue jsonRoot;
        try (InputStream jsonStream = handle.read()) {
            JsonReader reader = new JsonReader();
            jsonRoot = reader.parse(jsonStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file with path: " + path, e);
        }
        return jsonRoot;
    }
    
    private final RawConfig parent;
    private final JsonValue root;
    
    public RawConfig(RawConfig parent, JsonValue jsonRoot) {
        this.parent = parent;
        this.root = jsonRoot;
    }
    
    public RawConfig getParent() {
        return parent;
    }
    
    public float getFloat(String key) {
        JsonValue node = getNode(key);
        return node.asFloat();
    }
    
    public double getDouble(String key) {
        JsonValue node = getNode(key);
        return node.asDouble();
    }
    
    public int getInt(String key) {
        JsonValue node = getNode(key);
        return node.asInt();
    }

    public boolean getBoolean(String key) {
        return getNode(key).asBoolean();
    }
    
    public DoubleBasedExpression getExpression(String key) {
        return new DoubleBasedExpression(getString(key));
    }
    
    public <T extends Enum<T>> T getEnum(String key, Class<T> type) {
        return Enum.valueOf(type, getString(key));
    }
    
    public String getString(String key) {
        JsonValue node = getNode(key);
        return node.asString();
    }
    
    public boolean hasNode(String key) {
        return getNode(key) != null;
    }
    
    public RawConfig getChild(String key) {
        JsonValue node = getNode(key);
        if (node == null) {
            return null;
        }
        if (node.isValue()) {
            throw new IllegalArgumentException("The node found isn't an array or object."); 
        }
        return new RawConfig(this, node);
    }
    
    private JsonValue getNode(String key) {
        String[] split = key.split("\\.");
        JsonValue currentNode = root;
        for (String keyPart : split) {
            currentNode = currentNode.get(keyPart);
            if (currentNode == null) {
                break;
            }
        }
        return currentNode;
    }
    
    public Iterable<RawConfig> getChildren() {
        if (!isArray()) {
            throw new IllegalStateException("This config is no array");
        }
        Array<RawConfig> children = new Array<>(size());
        for (JsonValue node : root) {
            children.add(new RawConfig(this, node));
        }
        return children;
    }
    
    public boolean isNull() {
        return root == null || root.isNull();
    }
    
    public boolean isArray() {
        return root.isArray();
    }
    
    public int size() {
        return isArray() ? 0 : root.size;
    }
    
}
