package com.upseil.gdx.gwt.serialization;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntFloatMap;
import com.github.nmorel.gwtjackson.client.AbstractConfiguration;
import com.upseil.gdx.math.ExtendedRandom;

public class JsonConfiguration extends AbstractConfiguration {
    
    @Override
    protected void configure() {
        type(Array.class).serializer(ArraySerializer.class).deserializer(ArrayDeserializer.class);
        type(IntFloatMap.class).serializer(IntFloatMapSerializer.class).deserializer(IntFloatMapDeserializer.class);
        type(ExtendedRandom.class).serializer(ExtendedRandomSerializer.class).deserializer(ExtendedRandomDeserializer.class);
        type(Vector2.class).serializer(Vector2Serializer.class).deserializer(Vector2Deserializer.class);
    }
    
}
