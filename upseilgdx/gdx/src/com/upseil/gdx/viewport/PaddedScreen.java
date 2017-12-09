package com.upseil.gdx.viewport;

import com.badlogic.gdx.math.Rectangle;

public class PaddedScreen implements ScreenDivider {
    
    private int top;
    private int left;
    private int bottom;
    private int right;
    
    public PaddedScreen() {
        this(0);
    }

    public PaddedScreen(int padding) {
        this(padding, padding, padding, padding);
    }

    public PaddedScreen(int top, int left, int bottom, int right) {
        pad(top, left, bottom, right);
    }

    @Override
    public void getScreenPart(Rectangle screen) {
        float newX = screen.x + left;
        float newY = screen.y + bottom;
        float newWidth = screen.width - left - right;
        float newHeight = screen.height - top - bottom;
        screen.set(newX, newY, newWidth, newHeight);
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
    
    public PaddedScreen pad(int padding) {
        return pad(padding, padding, padding, padding);
    }
    
    public PaddedScreen pad(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        return this;
    }
    
}
