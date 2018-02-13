package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.config.AbstractConfig;
import com.upseil.gdx.config.RawConfig;

public class BorderBuilder extends AbstractDrawableBuilder {
    
    private static String texturePrefix;
    private static String namePrefix;
    private static String slimSuffix;

    private static String topSuffix;
    private static String leftSuffix;
    private static String bottomSuffix;
    private static String rightSuffix;
    
    public static void setConfig(Config config) {
        texturePrefix = config.getTexturePrefix();
        namePrefix = config.getNamePrefix();
        slimSuffix = config.getSlimSuffix();
        
        topSuffix = config.getTopSuffix();
        leftSuffix = config.getLeftSuffix();
        bottomSuffix = config.getBottomSuffix();
        rightSuffix = config.getRightSuffix();
    }
    
    private static BorderBuilder instance;
    
    public static BorderBuilder get(Skin skin, Config config) {
        setConfig(config);
        return get(skin);
    }
    
    public static BorderBuilder get(Skin skin) {
        if (instance == null) {
            instance = new BorderBuilder(skin);
            return instance;
        }
        return instance.reset();
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
        return namePrefix + (slim ? slimSuffix : "") + partSuffix;
    }

    private String getBorderName() {
        return getBaseName() + (color != null ? "-" + color : "");
    }

    private String getTextureName() {
        return texturePrefix + getBaseName();
    }
    
    public BorderBuilder reset() {
        color = null;
        slim = false;
        partSuffix = "";
        parts = null;
        return this;
    }
    
    public static class Config extends AbstractConfig {

        public Config(RawConfig rawConfig) {
            super(rawConfig);
        }

        public Config(String path) {
            super(path);
        }
        
        public String getTexturePrefix() {
            return getString("texturePrefix");
        }
        
        public String getNamePrefix() {
            return getString("namePrefix");
        }
        
        public String getSlimSuffix() {
            return getString("slimSuffix");
        }
        
        public String getTopSuffix() {
            return getString("topSuffix");
        }
        
        public String getLeftSuffix() {
            return getString("leftSuffix");
        }
        
        public String getBottomSuffix() {
            return getString("bottomSuffix");
        }
        
        public String getRightSuffix() {
            return getString("rightSuffix");
        }
        
    }
    
}
