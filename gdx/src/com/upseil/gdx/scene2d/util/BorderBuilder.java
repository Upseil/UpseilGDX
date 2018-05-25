package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.upseil.gdx.properties.EnumerizedJsonBasedProperties;

public class BorderBuilder extends AbstractDrawableBuilder {
    
    private static String texturePrefix;
    private static String namePrefix;
    private static String slimSuffix;

    private static String topSuffix;
    private static String leftSuffix;
    private static String bottomSuffix;
    private static String rightSuffix;
    
    public static void setConfig(Config config) {
        texturePrefix = config.get(BorderBuilderConfig.TexturePrefix);
        namePrefix = config.get(BorderBuilderConfig.NamePrefix);
        slimSuffix = config.get(BorderBuilderConfig.SlimSuffix);
        
        topSuffix = config.get(BorderBuilderConfig.TopSuffix);
        leftSuffix = config.get(BorderBuilderConfig.LeftSuffix);
        bottomSuffix = config.get(BorderBuilderConfig.BottomSuffix);
        rightSuffix = config.get(BorderBuilderConfig.RightSuffix);
    }
    
    private static BorderBuilder instance;
    
    public static BorderBuilder get(Skin skin, Config config) {
        setConfig(config);
        return get(skin);
    }
    
    public static BorderBuilder get(Skin skin) {
        if (instance == null) {
            instance = new BorderBuilder(skin);
        }
        return instance.reset(skin);
    }

    private String color;
    private boolean slim;
    private String partSuffix = "";
    
    private Array<Drawable> parts;

    public BorderBuilder(Skin skin) {
        super(skin);
    }

    public BorderBuilder color(String colorName) {
        color = colorName;
        return this;
    }
    
    public BorderBuilder slim() {
        return slim(true);
    }
    
    public BorderBuilder slim(boolean slim) {
        this.slim = slim;
        return this;
    }
    
    public BorderBuilder top() {
        addPart(topSuffix);
        return this;
    }
    
    public BorderBuilder left() {
        addPart(leftSuffix);
        return this;
    }
    
    public BorderBuilder bottom() {
        addPart(bottomSuffix);
        return this;
    }
    
    public BorderBuilder right() {
        addPart(rightSuffix);
        return this;
    }

    private void addPart(String partSuffix) {
        this.partSuffix = partSuffix;
        if (parts == null) {
            parts = new Array<>(4);
        }
        parts.add(getOrCreate(getBorderName(), getTextureName(), color));
        this.partSuffix = "";
    }

    @Override
    public Drawable build() {
        if (parts != null) {
            if (parts.size == 1) {
                return parts.first();
            }
            return new CompoundDrawable(parts);
        }
        return getOrCreate(getBorderName(), getTextureName(), color);
    }

    private String getBaseName() {
        StringBuilder baseName = string().append(namePrefix);
        if (slim) baseName.append(slimSuffix);
        baseName.append(partSuffix);
        return baseName.toString();
    }

    private String getBorderName() {
        String baseName = getBaseName();
        return color == null ? baseName : string().append(baseName).append("-").append(color).toString();
    }

    private String getTextureName() {
        String baseName = getBaseName();
        return string().append(texturePrefix).append(baseName).toString();
    }
    
    public BorderBuilder reset(Skin skin) {
        this.skin = skin;
        color = null;
        slim = false;
        partSuffix = "";
        parts = null;
        return this;
    }
    
    public static enum BorderBuilderConfig {
        TexturePrefix, NamePrefix, SlimSuffix, TopSuffix, LeftSuffix, BottomSuffix, RightSuffix
    }
    
    public static class Config extends EnumerizedJsonBasedProperties<BorderBuilderConfig> {
        
        public Config(JsonValue json) {
            super(json, BorderBuilderConfig.class);
        }
        
    }
    
}
