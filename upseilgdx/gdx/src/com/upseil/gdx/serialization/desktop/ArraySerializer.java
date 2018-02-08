package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class ArraySerializer extends StdSerializer<Array<Object>> implements ContextualSerializer {
    private static final long serialVersionUID = -8210849736817031467L;

    public ArraySerializer() {
        super(TypeFactory.defaultInstance().constructCollectionLikeType(Array.class, Object.class));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        return this;
    }

    @Override
    public void serialize(Array<Object> array, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (array == null) {
            serializers.defaultSerializeNull(gen);
            return;
        }
        
        gen.writeStartArray();
        for (Object value : array) {
            serializers.defaultSerializeValue(value, gen);
        }
        gen.writeEndArray();
    }
    
}
