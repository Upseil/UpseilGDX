package com.upseil.gdx.gwt.serialization;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntFloatMap;
import com.github.nmorel.gwtjackson.client.AbstractConfiguration;

public class JsonConfiguration extends AbstractConfiguration {
    
    @Override
    protected void configure() {
        type(Array.class).deserializer(ArrayDeserializer.class);
        type(IntFloatMap.class).serializer(IntFloatMapSerializer.class).deserializer(IntFloatMapDeserializer.class);
    }
    
}
