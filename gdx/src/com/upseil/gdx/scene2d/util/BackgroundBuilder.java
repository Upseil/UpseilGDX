package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.JsonValue;
import com.upseil.gdx.properties.EnumerizedJsonBasedProperties;

public class BackgroundBuilder extends AbstractDrawableBuilder {
    
    private static final Color tmpColor = new Color();
    
    private static String textureName;
    private static String namePrefix;
    
    private static String redPrefix;
    private static String greenPrefix;
    private static String bluePrefix;
    
    private static String alphaPrefix;
    private static Color defaultAlphaBase;
    
    private static String componentSeparator;
    
    public static void setConfig(Config config) {
        textureName = config.get(BackgroundBuilderConfig.TextureName);
        namePrefix = config.get(BackgroundBuilderConfig.NamePrefix);

        redPrefix = config.get(BackgroundBuilderConfig.RedPrefix);
        greenPrefix = config.get(BackgroundBuilderConfig.GreenPrefix);
        bluePrefix = config.get(BackgroundBuilderConfig.BluePrefix);
        
        alphaPrefix = config.get(BackgroundBuilderConfig.AlphaPrefix);
        defaultAlphaBase = Colors.get(config.get(BackgroundBuilderConfig.DefaultAlphaBase));
        
        componentSeparator = config.get(BackgroundBuilderConfig.ComponentSeparator);
    }
    
    private static BackgroundBuilder instance;
    
    public static BackgroundBuilder get(Skin skin, Config config) {
        setConfig(config);
        return get(skin);
    }
    
    public static BackgroundBuilder get(Skin skin) {
        if (instance == null) {
            instance = new BackgroundBuilder(skin);
        }
        return instance.reset(skin);
    }

    public static Drawable byColor(Skin skin, String colorName) {
        return getOrCreate(skin, string().append(namePrefix).append(componentSeparator).append(colorName).toString(), textureName, colorName);
    }
    
    public static Drawable byColor(Skin skin, String colorName, double alpha) {
        return get(skin).color(colorName).alpha(alpha).build();
    }
    
    public static Drawable byColor(Skin skin, Color color) {
        return get(skin).color(color).build();
    }
    
    public static Drawable byColor(Skin skin, Color color, double alpha) {
        return get(skin).color(color).alpha(alpha).build();
    }

    public static Drawable byGrayscale(Skin skin, float grayscale) {
        return get(skin).color(tmpColor.set(grayscale, grayscale, grayscale, 1)).build();
    }

    public static Drawable byGrayscale(Skin skin, float grayscale, double alpha) {
        return get(skin).color(tmpColor.set(grayscale, grayscale, grayscale, 1)).alpha(alpha).build();
    }
    
    public static Drawable byAlpha(Skin skin, double alpha) {
        return get(skin).alpha(alpha).build();
    }
    
    private String baseColorName;
    private Color baseColor;
    private double alpha = -1;
    private BorderBuilder borderBuilder;

    public BackgroundBuilder(Skin skin) {
        super(skin);
    }
    
    public BackgroundBuilder color(String name) {
        baseColorName = name;
        baseColor = null;
        return this;
    }
    
    public BackgroundBuilder color(Color color) {
        baseColorName = null;
        baseColor = color;
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
        if (baseColorName == null && baseColor == null && alpha < 0) {
            return null;
        }
        
        String colorName = getColorName();
        ensureColorExists(colorName);
        
        String drawableName = string().append(namePrefix).append(componentSeparator).append(colorName).toString();
        return getOrCreate(drawableName, textureName, colorName);
    }

    private String getColorName() {
        StringBuilder name = string();
        double alpha = this.alpha;
        boolean hasBaseColor = false;
        
        if (baseColorName != null) {
            name.append(baseColorName);
            if (alpha >= 0) alpha *= skin.getColor(baseColorName).a;
            hasBaseColor = true;
        }
        
        // TODO Extract and move to AbstractDrawableBuilder
        if (baseColor != null) {
            int rInt = (int) (baseColor.r * 255);
            int gInt = (int) (baseColor.g * 255);
            int bInt = (int) (baseColor.b * 255);
            if (rInt == gInt && gInt == bInt) {
                name.append(redPrefix).append(bluePrefix).append(greenPrefix).append(rInt);
            } else {
                name.append(redPrefix).append(rInt).append(componentSeparator);
                name.append(greenPrefix).append(gInt).append(componentSeparator);
                name.append(bluePrefix).append(bInt);
            }
            
            if (!MathUtils.isEqual(baseColor.a, 1)) {
                alpha = alpha >= 0 ? alpha * baseColor.a : baseColor.a;
            }
        }
        
        if (alpha >= 0) {
            name.append(hasBaseColor ? componentSeparator : "").append(alphaPrefix).append((int) (alpha * 255));
        }
        return name.toString();
    }

    private void ensureColorExists(String colorName) {
        if ((baseColor != null || alpha >= 0) && !skin.has(colorName, Color.class)) {
            Color baseColor = defaultAlphaBase;
            if (baseColorName != null) baseColor = skin.getColor(baseColorName);
            if (this.baseColor != null) baseColor = this.baseColor;
            
            skin.add(colorName, baseColor.cpy().mul(1, 1, 1, alpha >= 0 ? (float) alpha : 1));
        }
    }
    
    public BackgroundBuilder reset(Skin skin) {
        this.skin = skin;
        return reset();
    }

    public BackgroundBuilder reset() {
        baseColorName = null;
        baseColor = null;
        alpha = -1;
        borderBuilder = null;
        return this;
    }
    
    public static enum BackgroundBuilderConfig {
        TextureName, NamePrefix, RedPrefix, GreenPrefix, BluePrefix, AlphaPrefix, DefaultAlphaBase, ComponentSeparator
    }
    
    public static class Config extends EnumerizedJsonBasedProperties<BackgroundBuilderConfig> {
        
        public Config(JsonValue json) {
            super(json, true, BackgroundBuilderConfig.class);
        }
        
    }
    
}
