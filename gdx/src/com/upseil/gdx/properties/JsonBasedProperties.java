package com.upseil.gdx.properties;

import com.badlogic.gdx.utils.JsonValue;

public class JsonBasedProperties extends AbstractJsonBasedProperties<String> {

    public JsonBasedProperties(JsonValue json) {
        super(json);
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
