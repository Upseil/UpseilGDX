package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class CompoundDrawable implements Drawable {
    
    public Array<Drawable> drawables;
    public int masterIndex = -1;
    
    public CompoundDrawable(Drawable... drawables) {
        this.drawables = new Array<>(drawables.length);
        for (Drawable drawable : drawables) {
            this.drawables.add(drawable);
        }
    }
    
    public CompoundDrawable(Array<Drawable> drawables) {
        this.drawables = drawables;
    }

    public CompoundDrawable() { }
    
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        for (Drawable drawable : drawables) {
            drawable.draw(batch, x, y, width, height);
        }
    }
    
    @Override
    public float getLeftWidth() {
        return getMaster().getLeftWidth();
    }

    @Override
    public void setLeftWidth(float leftWidth) {
        for (Drawable drawable : drawables) {
            drawable.setLeftWidth(leftWidth);
        }
    }
    
    @Override
    public float getRightWidth() {
        return getMaster().getRightWidth();
    }
    
    @Override
    public void setRightWidth(float rightWidth) {
        for (Drawable drawable : drawables) {
            drawable.setRightWidth(rightWidth);
        }
    }
    
    @Override
    public float getTopHeight() {
        return getMaster().getTopHeight();
    }
    
    @Override
    public void setTopHeight(float topHeight) {
        for (Drawable drawable : drawables) {
            drawable.setTopHeight(topHeight);
        }
    }
    
    @Override
    public float getBottomHeight() {
        return getMaster().getBottomHeight();
    }
    
    @Override
    public void setBottomHeight(float bottomHeight) {
        for (Drawable drawable : drawables) {
            drawable.setBottomHeight(bottomHeight);
        }
    }
    
    @Override
    public float getMinWidth() {
        return getMaster().getMinWidth();
    }
    
    @Override
    public void setMinWidth(float minWidth) {
        for (Drawable drawable : drawables) {
            drawable.setMinWidth(minWidth);
        }
    }
    
    @Override
    public float getMinHeight() {
        return getMaster().getMinHeight();
    }
    
    @Override
    public void setMinHeight(float minHeight) {
        for (Drawable drawable : drawables) {
            drawable.setMinHeight(minHeight);
        }
    }
    
    private Drawable getMaster() {
        int index = masterIndex < 0 ? drawables.size - 1 : masterIndex;
        return drawables.get(index);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append("[drawables=").append(drawables).append(", masterIndex=").append(masterIndex).append("]");
        return builder.toString();
    }
    
}
