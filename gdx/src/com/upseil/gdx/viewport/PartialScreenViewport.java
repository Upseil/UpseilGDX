package com.upseil.gdx.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class PartialScreenViewport extends PartialViewport {
    
    private float unitsPerPixel;
    
    public PartialScreenViewport(ScreenDivider screenDivider) {
        this(screenDivider, new OrthographicCamera());
    }
    
    public PartialScreenViewport(ScreenDivider screenDivider, Camera camera) {
        super(screenDivider, camera);
        unitsPerPixel = 1;
        setCamera(camera);
    }
    
    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {
        Rectangle screenPart = getScreenPart();
        screenPart.set(0, 0, screenWidth, screenHeight);
        getScreenDivider().getScreenPart(screenPart);
        
        int width = Math.round(screenPart.width);
        int height = Math.round(screenPart.height);
        setScreenBounds(Math.round(screenPart.x), Math.round(screenPart.y), width, height);
        setWorldSize(width * unitsPerPixel, height * unitsPerPixel);
        apply(centerCamera);
    }

    public float getUnitsPerPixel() {
        return unitsPerPixel;
    }

    public void setUnitsPerPixel(float unitsPerPixel) {
        this.unitsPerPixel = unitsPerPixel;
    }
    
}
