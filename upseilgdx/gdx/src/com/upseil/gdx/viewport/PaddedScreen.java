package com.upseil.gdx.viewport;

import com.badlogic.gdx.math.Rectangle;

public class PaddedScreen implements ScreenDivider {
    
    private int top;
    private int left;
    private int bottom;
    private int right;

    @Override
    public Rectangle getScreenPart(int screenWidth, int screenHeight) {
        return new Rectangle(left, bottom, screenWidth - left - right, screenHeight - top - bottom);
    }

    public int getTop() {
        return top;
    }

    public PaddedScreen padTop(int top) {
        this.top = top;
        return this;
    }

    public int getLeft() {
        return left;
    }

    public PaddedScreen padLeft(int left) {
        this.left = left;
        return this;
    }

    public int getBottom() {
        return bottom;
    }

    public PaddedScreen padBottom(int bottom) {
        this.bottom = bottom;
        return this;
    }

    public int getRight() {
        return right;
    }

    public PaddedScreen padRight(int right) {
        this.right = right;
        return this;
    }
    
    public PaddedScreen pad(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        return this;
    }
    
}
