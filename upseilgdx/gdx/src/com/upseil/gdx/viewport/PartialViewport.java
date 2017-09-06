package com.upseil.gdx.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
        screenPart.set(0, 0, screenWidth, screenHeight);
        divider.getScreenPart(screenPart);
        Vector2 viewportSize = calculateViewportSize(screenPart);
        
        int viewportWidth = Math.round(viewportSize.x);
        int viewportHeight = Math.round(viewportSize.y);
        int screenX = Math.round(screenPart.x + (screenPart.width - viewportSize.x) / 2);
        int screenY = Math.round(screenPart.y + (screenPart.height - viewportSize.y) / 2);
        
        setScreenBounds(screenX, screenY, viewportWidth, viewportHeight);
        apply(centerCamera);
    }

    protected abstract Vector2 calculateViewportSize(Rectangle screenPart);

    public ScreenDivider getDivider() {
        return divider;
    }

    public void setDivider(ScreenDivider divider) {
        this.divider = divider;
    }
    
}
