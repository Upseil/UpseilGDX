package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Disposable;

public class TextureDrawable extends BaseDrawable implements TransformDrawable, Disposable {
    
    private Texture texture;
    private boolean flipX;
    private boolean flipY;
    
    public TextureDrawable() {
    }
    
    public TextureDrawable(Pixmap pixmap) {
        this(new Texture(pixmap));
    }

    public TextureDrawable(Texture texture) {
        setTexture(texture);
    }
    
    public TextureDrawable(TextureDrawable drawable) {
        super(drawable);
        setTexture(drawable.texture);
    }
    
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.draw(texture, x, y, width, height, 0, 0, texture.getWidth(), texture.getHeight(), flipX, flipY);
    }
    
    @Override
    public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, 0, 0, texture.getWidth(), texture.getHeight(), flipX, flipY);
    }
    
    public Texture getTexture() {
        return texture;
    }

    private void setTexture(Texture texture) {
        this.texture = texture;
        setMinWidth(texture.getWidth());
        setMinHeight(texture.getHeight());
    }
    
    public boolean flipX() {
        return flipX;
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }

    public boolean flipY() {
        return flipY;
    }

    public void setFlipY(boolean flipY) {
        this.flipY = flipY;
    }

    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
    
}
