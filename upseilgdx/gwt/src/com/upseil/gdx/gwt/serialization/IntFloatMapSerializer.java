package com.upseil.gdx.gwt.serialization;

import java.util.Iterator;

import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntFloatMap.Entry;
import com.github.nmorel.gwtjackson.client.JsonSerializationContext;
import com.github.nmorel.gwtjackson.client.JsonSerializer;
import com.github.nmorel.gwtjackson.client.JsonSerializerParameters;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

public class IntFloatMapSerializer extends JsonSerializer<IntFloatMap> {
    
    private static final IntFloatMapSerializer instance = new IntFloatMapSerializer();
    public static IntFloatMapSerializer getInstance() {
        return instance;
    }
    
    private IntFloatMapSerializer() {
    }
    
    @Override
    protected void doSerialize(JsonWriter writer, IntFloatMap map, JsonSerializationContext ctx, JsonSerializerParameters params) {
        writer.beginObject();
        
        Iterator<Entry> iterator = map.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            writer.name(Integer.toString(entry.key)).value(entry.value);
        }
        
        writer.endObject();
    }
    
}
