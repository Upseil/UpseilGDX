package com.upseil.gdx.viewport;

import com.badlogic.gdx.math.Rectangle;

public interface ScreenDivider {
    
    Rectangle getScreenPart(int screenWidth, int screenHeight);
    
}
