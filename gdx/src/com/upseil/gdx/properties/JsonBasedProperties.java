package com.upseil.gdx.properties;

import com.badlogic.gdx.utils.JsonValue;

public class JsonBasedProperties extends AbstractJsonBasedProperties<String> {

    public JsonBasedProperties(JsonValue json) {
        super(json);
    }

    public JsonBasedProperties(JsonValue json, boolean strict) {
        super(json, strict);
    }

    @Override
    protected String keyToString(String key) {
        return key;
    }

    @Override
    protected String stringToKey(String string) {
        return string;
    }
    
}
