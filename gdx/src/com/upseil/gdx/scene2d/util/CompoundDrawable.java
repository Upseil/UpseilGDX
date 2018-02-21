package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class CompoundDrawable implements Drawable {
    
    public Array<Drawable> drawables;
    private float leftWidth, rightWidth, topHeight, bottomHeight, minWidth, minHeight;
    
    private boolean valuesInvalid;
    
    public CompoundDrawable(Drawable... drawables) {
        this.drawables = new Array<>(drawables.length);
        for (Drawable drawable : drawables) {
            this.drawables.add(drawable);
            adjustValues(drawable);
        }
    }
    
    private void adjustValues(Drawable drawable) {
        float leftWidth = drawable.getLeftWidth();
        float rightWidth = drawable.getRightWidth();
        float topHeight = drawable.getTopHeight();
        float bottomHeight = drawable.getBottomHeight();
        float minWidth = drawable.getMinWidth();
        float minHeight = drawable.getMinHeight();

        if (leftWidth > this.leftWidth) this.leftWidth = leftWidth;
        if (rightWidth > this.rightWidth) this.rightWidth = rightWidth;
        if (topHeight > this.topHeight) this.topHeight = topHeight;
        if (bottomHeight > this.bottomHeight) this.bottomHeight = bottomHeight;
        if (minWidth > this.minWidth) this.minWidth = minWidth;
        if (minHeight > this.minHeight) this.minHeight = minHeight;
    }

    public CompoundDrawable(Array<Drawable> drawables) {
        this.drawables = drawables;
        for (Drawable drawable : this.drawables) {
            adjustValues(drawable);
        }
    }

    public CompoundDrawable() {
        valuesInvalid = true;
    }
    
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        validate();
        for (Drawable drawable : drawables) {
            drawable.draw(batch, x, y, width, height);
        }
    }
    
    @Override
    public float getLeftWidth() {
        validate();
        return leftWidth;
    }

    @Override
    public void setLeftWidth(float leftWidth) {
        validate();
        for (Drawable drawable : drawables) {
            drawable.setLeftWidth(leftWidth);
        }
        if (leftWidth > this.leftWidth) this.leftWidth = leftWidth;
    }
    
    @Override
    public float getRightWidth() {
        validate();
        return rightWidth;
    }
    
    @Override
    public void setRightWidth(float rightWidth) {
        validate();
        for (Drawable drawable : drawables) {
            drawable.setRightWidth(rightWidth);
        }
        if (rightWidth > this.rightWidth) this.rightWidth = rightWidth;
    }
    
    @Override
    public float getTopHeight() {
        validate();
        return topHeight;
    }
    
    @Override
    public void setTopHeight(float topHeight) {
        validate();
        for (Drawable drawable : drawables) {
            drawable.setTopHeight(topHeight);
        }
        if (topHeight > this.topHeight) this.topHeight = topHeight;
    }
    
    @Override
    public float getBottomHeight() {
        validate();
        return bottomHeight;
    }
    
    @Override
    public void setBottomHeight(float bottomHeight) {
        validate();
        for (Drawable drawable : drawables) {
            drawable.setBottomHeight(bottomHeight);
        }
        if (bottomHeight > this.bottomHeight) this.bottomHeight = bottomHeight;
    }
    
    @Override
    public float getMinWidth() {
        validate();
        return minWidth;
    }
    
    @Override
    public void setMinWidth(float minWidth) {
        validate();
        for (Drawable drawable : drawables) {
            drawable.setMinWidth(minWidth);
        }
        if (minWidth > this.minWidth) this.minWidth = minWidth;
    }
    
    @Override
    public float getMinHeight() {
        validate();
        return minHeight;
    }
    
    @Override
    public void setMinHeight(float minHeight) {
        validate();
        for (Drawable drawable : drawables) {
            drawable.setMinHeight(minHeight);
        }
        if (minHeight > this.minHeight) this.minHeight = minHeight;
    }
    

    private void validate() {
        if (!valuesInvalid) return;
        
        for (Drawable drawable : this.drawables) {
            adjustValues(drawable);
        }
        valuesInvalid = false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append(drawables);
        return builder.toString();
    }
    
}
