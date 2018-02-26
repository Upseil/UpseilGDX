package com.upseil.gdx.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public abstract class PartialWorldViewport extends PartialViewport {
    
    private int alignment;

    public PartialWorldViewport (ScreenDivider divider, float worldWidth, float worldHeight) {
        this(divider, worldWidth, worldHeight, new OrthographicCamera());
    }

    public PartialWorldViewport(ScreenDivider divider, float worldWidth, float worldHeight, Camera camera) {
        super(divider, camera);
        alignment = Align.center;
        setWorldSize(worldWidth, worldHeight);
        setCamera(camera);
    }
    
    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        Rectangle screenPart = getScreenPart();
        screenPart.set(0, 0, screenWidth, screenHeight);
        getScreenDivider().getScreenPart(screenPart);
        Vector2 viewportSize = calculateViewportSize(screenPart);
        
        float screenX;
        if ((alignment & Align.left) != 0) {
            screenX = screenPart.x;
        } else if ((alignment & Align.right) != 0) {
            screenX = screenPart.x + screenPart.width - viewportSize.x;
        } else {
            screenX = screenPart.x + (screenPart.width - viewportSize.x) / 2;
        }
        
        float screenY;
        if ((alignment & Align.bottom) != 0) {
            screenY = screenPart.y;
        } else if ((alignment & Align.top) != 0) {
            screenY = screenPart.y + screenPart.height - viewportSize.y;
        } else {
            screenY = screenPart.y + (screenPart.height - viewportSize.y) / 2;
        }
        
        setScreenBounds(Math.round(screenX), Math.round(screenY), Math.round(viewportSize.x), Math.round(viewportSize.y));
        apply(centerCamera);
    }

    protected abstract Vector2 calculateViewportSize(Rectangle screenPart);

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }
    
}
