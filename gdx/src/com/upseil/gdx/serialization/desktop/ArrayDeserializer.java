package com.upseil.gdx.serialization.desktop;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

public class ArrayDeserializer extends JsonDeserializer<Array<Object>> implements ContextualDeserializer {

    private final JavaType valueType;
    
    public ArrayDeserializer() {
        this(null);
    }
    
    private ArrayDeserializer(JavaType valueType) {
        this.valueType = valueType;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JavaType valueType;
        if (property == null) {
            valueType = ctxt.getContextualType().containedType(0);
        } else {
            valueType = property.getType().containedType(0);
        }
        return new ArrayDeserializer(valueType);
    }

    @Override
    public Array<Object> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (p.currentTokenId() == JsonTokenId.ID_NULL) {
            return null;
        }
        
        if (p.currentToken() == JsonToken.START_ARRAY) {
            Array<Object> array = new Array<>();
            while (p.nextToken() != JsonToken.END_ARRAY) {
                array.add(ctxt.readValue(p, valueType));
            }
            return array;
        } else {
            throw new JsonParseException(p, "Can't deserialize a " + Array.class.getName() + " out of " + p.currentToken() + " token");
        }
    }
    
}
