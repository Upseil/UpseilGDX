package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public abstract class AbstractDrawableBuilder {

    // TODO This shouldn't be final, since this locks the static instances to a single skin
    protected final Skin skin;

    public AbstractDrawableBuilder(Skin skin) {
        this.skin = skin;
    }
    
    public abstract Drawable build();
    
    protected Drawable getOrCreate(String drawableName, String baseTextureName, String colorName) {
        return getOrCreate(skin, drawableName, baseTextureName, colorName);
    }
    
    protected static Drawable getOrCreate(Skin skin, String drawableName, String baseTextureName, String colorName) {
        Drawable drawable = skin.optional(drawableName, Drawable.class);
        if (drawable == null) {
            drawable = skin.newDrawable(baseTextureName, skin.getColor(colorName));
            skin.add(drawableName, drawable, Drawable.class);
        }
        return drawable;
    }
    
}
