package com.upseil.gdx.serialization.desktop;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.IntFloatMap;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class IntFloatMapDeserializer extends StdDeserializer<IntFloatMap> {
    private static final long serialVersionUID = -1829232448733651628L;

    protected IntFloatMapDeserializer() {
        super(IntFloatMap.class);
    }

    @Override
    public IntFloatMap deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode json = p.getCodec().readTree(p);

        IntFloatMap map = new IntFloatMap();
        Iterator<Entry<String, JsonNode>> fields = json.fields();
        while (fields.hasNext()) {
            Entry<String, JsonNode> entry = fields.next();
            int key = Integer.parseInt(entry.getKey());
            map.put(key, entry.getValue().floatValue());
        }
        return map;
    }
    
}
