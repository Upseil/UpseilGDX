package com.upseil.gdx.viewport;

import com.badlogic.gdx.math.Rectangle;

public class CompoundScreenDivider implements ScreenDivider {
    
    private final ScreenDivider[] dividers;
    
    public CompoundScreenDivider(ScreenDivider... dividers) {
        this.dividers = dividers;
    }
    
    @Override
    public Rectangle getScreenPart(Rectangle screen) {
        for (ScreenDivider divider : dividers) {
            divider.getScreenPart(screen);
        }
        return screen;
    }
    
}
