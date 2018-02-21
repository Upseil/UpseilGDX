package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.upseil.gdx.config.AbstractConfig;
import com.upseil.gdx.config.RawConfig;

public class DividerBuilder extends AbstractDrawableBuilder {
    
    private static String texturePrefix = "t-";
    private static String textureName = "line";
    private static String dividerName = "divider";
    private static String slimSuffix = "-slim";
    
    public static void setConfig(Config config) {
        texturePrefix = config.getTexturePrefix();
        textureName = config.getTextureName();
        dividerName = config.getDividerName();
        slimSuffix = config.getSlimSuffix();
    }
    
    private static DividerBuilder instance;
    
    public static DividerBuilder get(Skin skin, Config config) {
        setConfig(config);
        return get(skin);
    }
    
    public static DividerBuilder get(Skin skin) {
        if (instance == null) {
            instance = new DividerBuilder(skin);
            return instance;
        }
        return instance.reset();
    }
    
    private String color;
    private Orientation orientation;
    private boolean slim;

    public enum Orientation { 
        Horizontal("h"), Vertical("v");
        
        private final String prefix;
        private Orientation(String prefix) {
            this.prefix = prefix;
        }
    };
    
    public DividerBuilder(Skin skin) {
        super(skin);
        color = "t-highlight";
    }
    
    public DividerBuilder color(String color) {
        this.color = color;
        return this;
    }

	public DividerBuilder horizontal() {
		return orientation(Orientation.Horizontal);
	}

	public DividerBuilder vertical() {
		return orientation(Orientation.Vertical);
	}
    
    public DividerBuilder orientation(Orientation orientation) {
        this.orientation = orientation;
        return this;
    }
    
    public DividerBuilder slim() {
        return slim(true);
    }
    
    public DividerBuilder slim(boolean slim) {
        this.slim = slim;
        return this;
    }

    @Override
    public Drawable build() {
        return getOrCreate(getDividerName(), getTextureName(), color);
    }
    
    private String getTextureName() {
        return texturePrefix + orientation.prefix + textureName + (slim ? slimSuffix : "");
    }
    
    private String getDividerName() {
        return orientation.prefix + dividerName + (slim ? slimSuffix : "") + (color != null ? "-" + color : "");
    }
    
    public DividerBuilder reset() {
        color = "t-highlight";
        orientation = null;
        slim = false;
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
        
        public String getTextureName() {
            return getString("textureName");
        }
        
        public String getDividerName() {
            return getString("dividerName");
        }
        
        public String getSlimSuffix() {
            return getString("slimSuffix");
        }
        
    }
    
}
