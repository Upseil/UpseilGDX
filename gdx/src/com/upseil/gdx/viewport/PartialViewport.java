package com.upseil.gdx.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class PartialViewport extends Viewport {
    
    private ScreenDivider screenDivider;
    private final Rectangle screenPart;
    
    public PartialViewport(ScreenDivider screenDivider) {
        this(screenDivider, new OrthographicCamera());
    }
    
    public PartialViewport(ScreenDivider screenDivider, Camera camera) {
        this.screenDivider = screenDivider;
        screenPart = new Rectangle();
    }

    public ScreenDivider getScreenDivider() {
        return screenDivider;
    }
    
    public void setScreenDivider(ScreenDivider screenDivider) {
        this.screenDivider = screenDivider;
    }

    protected Rectangle getScreenPart() {
        return screenPart;
    }
    
}
