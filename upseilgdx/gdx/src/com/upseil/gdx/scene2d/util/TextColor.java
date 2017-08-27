package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.utils.ObjectMap;

public class TextColor {
    
    public static final TextColor None = new TextColor(null);

    private static final ObjectMap<String, TextColor> byNameCache = new ObjectMap<>();
    
    public static TextColor byName(String name) {
        if (name == null) {
            return None;
        }
        if (Colors.get(name) == null) {
            new IllegalArgumentException("No color found with name " + name);
        }
        
        TextColor textColor = byNameCache.get(name);
        if (textColor == null) {
            textColor = new TextColor(name);
            byNameCache.put(name, textColor);
        }
        return textColor;
    }
    
    private final String name;
    private final String markup;

    private TextColor(String name) {
        this.name = name;
        markup = name == null ? "" : "[" + name + "]";
    }
    
    public String getColorName() {
        return name;
    }
    
    public Color getColor() {
        return name == null ? null : Colors.get(name);
    }
    
    public String asMarkup() {
        return markup;
    }
    
}