package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.upseil.gdx.config.AbstractConfig;
import com.upseil.gdx.config.RawConfig;

public class BackgroundBuilder extends AbstractDrawableBuilder {
    
    private static String textureName;
    private static String namePrefix;
    private static String transparentPrefix;
    
    public static void setConfig(Config config) {
        textureName = config.getTextureName();
        namePrefix = config.getNamePrefix();
        transparentPrefix = config.getTransparentPrefix();
    }
    
    private static BackgroundBuilder instance;
    
    public static BackgroundBuilder get(Skin skin, Config config) {
        setConfig(config);
        return get(skin);
    }
    
    public static BackgroundBuilder get(Skin skin) {
        if (instance == null) {
            instance = new BackgroundBuilder(skin);
            return instance;
        }
        return instance.reset();
    }

    public static Drawable byColor(Skin skin, String colorName) {
        return getOrCreate(skin, namePrefix + colorName, textureName, colorName);
    }
    
    public static Drawable byColor(Skin skin, String colorName, double alpha) {
        return get(skin).color(colorName).alpha(alpha).build();
    }
    
    public static Drawable byAlpha(Skin skin, double alpha) {
        return get(skin).alpha(alpha).build();
    }
    
    private String baseColorName;
    private double alpha = -1;
    private BorderBuilder borderBuilder;

    public BackgroundBuilder(Skin skin) {
        super(skin);
    }
    
    public BackgroundBuilder color(String name) {
        baseColorName = name;
        return this;
    }
    
    public BackgroundBuilder alpha(double alpha) {
        this.alpha = MathUtils.clamp(alpha, 0, 1.0);
        return this;
    }
    
    public BorderBuilder bordered() {
        borderBuilder = new BorderBuilder(skin) {
            @Override
            public Drawable build() {
                Drawable background = buildBackground();
                return background == null ? super.build() : new CompoundDrawable(background, super.build());
            }
        };
        return borderBuilder;
    }
    
    public BorderBuilder bordered(String colorName) {
        return bordered().color(colorName);
    }
    
    @Override
    public Drawable build() {
        return borderBuilder != null ? borderBuilder.build() : buildBackground();
    }
    
    private Drawable buildBackground() {
        if (baseColorName == null && alpha < 0) {
            return null;
        }
        
        String colorName = getColorName();
        ensureAlphaColorExists(colorName);
        return getOrCreate(namePrefix + colorName, textureName, colorName);
    }

    private String getColorName() {
        StringBuilder name = new StringBuilder();
        boolean hasBaseColor = baseColorName != null;
        if (hasBaseColor) {
            name.append(baseColorName);
        }
        if (alpha >= 0) {
            double effectiveAlpha = hasBaseColor ? skin.getColor(baseColorName).a * alpha : alpha;
            name.append(hasBaseColor ? "-" : "").append(transparentPrefix).append((int) (effectiveAlpha * 255));
        }
        return name.toString();
    }

    private void ensureAlphaColorExists(String colorName) {
        if (alpha >= 0 && !skin.has(colorName, Color.class)) {
            Color newColor = baseColorName != null ? skin.getColor(baseColorName).cpy() : Color.BLACK.cpy();
            newColor.a *= alpha;
            skin.add(colorName, newColor);
        }
    }
    
    public BackgroundBuilder reset() {
        baseColorName = null;
        alpha = -1;
        borderBuilder = null;
        return this;
    }
    
    public static class Config extends AbstractConfig {
        
        public Config(String path) {
            super(path);
        }

        public Config(RawConfig rawConfig) {
            super(rawConfig);
        }
        
        public String getTextureName() {
            return getRawConfig().getString("textureName");
        }
        
        public String getNamePrefix() {
            return getRawConfig().getString("namePrefix");
        }
        
        public String getTransparentPrefix() {
            return getRawConfig().getString("transparentPrefix");
        }
        
    }
    
}
