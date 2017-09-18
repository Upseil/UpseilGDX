package com.upseil.gdx.serialization.desktop;

import java.io.IOException;
import java.util.Iterator;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntFloatMap.Entry;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class IntFloatMapSerializer extends StdSerializer<IntFloatMap> {
    private static final long serialVersionUID = 7762293895052221015L;

    public IntFloatMapSerializer() {
        super(IntFloatMap.class);
    }
    
    @Override
    public void serialize(IntFloatMap map, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        
        Iterator<Entry> iterator = map.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            gen.writeNumberField(Integer.toString(entry.key), entry.value);
        }
        
        gen.writeEndObject();
    }
    
}
