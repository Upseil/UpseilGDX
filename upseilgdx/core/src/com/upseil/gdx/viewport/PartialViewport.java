package com.upseil.gdx.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class PartialViewport extends Viewport {
    
    private ScreenDivider divider;
    private final Rectangle screenPart;

    public PartialViewport (ScreenDivider divider, float worldWidth, float worldHeight) {
        this(divider, worldWidth, worldHeight, new OrthographicCamera());
    }

    public PartialViewport(ScreenDivider divider, float worldWidth, float worldHeight, Camera camera) {
        this.divider = divider;
        screenPart = new Rectangle();
        setWorldSize(worldWidth, worldHeight);
        setCamera(camera);
    }
    
    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        screenPart.set(divider.getScreenPart(screenWidth, screenHeight));
        updateScreenBounds(screenPart);
        apply(centerCamera);
    }

    protected abstract void updateScreenBounds(Rectangle screenPart);
    
}
