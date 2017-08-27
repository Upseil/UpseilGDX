package com.upseil.gdx.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;

public class ImageActor extends Actor {
    
    private Drawable drawable;
    
    public ImageActor() {
        this(null);
    }

    public ImageActor(Drawable drawable) {
        this.drawable = drawable;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (drawable == null) {
            return;
        }

        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();
        float scaleX = getScaleX();
        float scaleY = getScaleY();

        if (drawable instanceof TransformDrawable) {
            float rotation = getRotation();
            if (scaleX != 1 || scaleY != 1 || rotation != 0) {
                ((TransformDrawable)drawable).draw(batch, x, y, getOriginX(), getOriginY(), width, height, scaleX, scaleY, rotation);
                return;
            }
        }
        drawable.draw(batch, x, y, width * scaleX, height * scaleY);
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
    
}
