package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.JsonValue;
import com.upseil.gdx.properties.EnumerizedJsonBasedProperties;

public class DividerBuilder extends AbstractDrawableBuilder {
    
    private static String texturePrefix = "t-";
    private static String textureName = "line";
    private static String dividerName = "divider";
    private static String slimSuffix = "-slim";
    
    public static void setConfig(Config config) {
        texturePrefix = config.get(DividerBuilderConfig.TexturePrefix);
        textureName = config.get(DividerBuilderConfig.TextureName);
        dividerName = config.get(DividerBuilderConfig.DividerName);
        slimSuffix = config.get(DividerBuilderConfig.SlimSuffix);
    }
    
    private static DividerBuilder instance;
    
    public static DividerBuilder get(Skin skin, Config config) {
        setConfig(config);
        return get(skin);
    }
    
    public static DividerBuilder get(Skin skin) {
        if (instance == null) {
            instance = new DividerBuilder(skin);
        }
        return instance.reset(skin);
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
    
    private String getDividerName() {
        StringBuilder name = string().append(orientation.prefix).append(dividerName);
        if (slim) name.append(slimSuffix);
        if (color != null) name.append("-").append(color);
        return name.toString();
    }
    
    private String getTextureName() {
        StringBuilder name = string().append(texturePrefix).append(orientation.prefix).append(textureName);
        if (slim) name.append(slimSuffix);
        return name.toString();
    }
    
    public DividerBuilder reset(Skin skin) {
        this.skin = skin;
        color = "t-highlight";
        orientation = null;
        slim = false;
        return this;
    }
    
    public static enum DividerBuilderConfig {
        TexturePrefix, TextureName, DividerName, SlimSuffix
    }
    
    public static class Config extends EnumerizedJsonBasedProperties<DividerBuilderConfig> {
        
        public Config(JsonValue json) {
            super(json, true, DividerBuilderConfig.class);
        }
        
    }
    
}
