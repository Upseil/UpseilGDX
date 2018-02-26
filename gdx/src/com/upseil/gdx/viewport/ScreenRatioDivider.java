package com.upseil.gdx.viewport;

import com.badlogic.gdx.math.Rectangle;
import com.upseil.gdx.util.FloatFloatPair;

public class ScreenRatioDivider implements ScreenDivider {
    
    private float ratio; // width / height
    
    public ScreenRatioDivider(String ratio) {
        this(parseToRatio(ratio));
    }

    public ScreenRatioDivider(FloatFloatPair ratio) {
        this(ratio.getA(), ratio.getB());
    }
    
    public ScreenRatioDivider(float widthRatio, float heightRatio) {
        ratio = widthRatio / heightRatio;
    }
    
    @Override
    public Rectangle getScreenPart(Rectangle screen) {
        float screenRatio = screen.getAspectRatio();
        if (ratio < screenRatio) { // Taller than wide
            float newWidth = screen.height * ratio;
            screen.set(screen.x + (screen.width - newWidth) / 2, screen.y, newWidth, screen.height);
        } else { // Wider than tall
            float newHeight = screen.width / ratio;
            screen.set(screen.x, screen.y + (screen.height - newHeight) / 2, screen.width, newHeight);
        }
        return screen;
    }
    
    private static FloatFloatPair parseToRatio(String ratio) {
        int separatorIndex = ratio.indexOf(':');
        boolean isRatioValid = separatorIndex > 0;
        Throwable error = null;
        
        float widthRatio = 0;
        float heightRatio = 0;
        if (isRatioValid) {
            try {
                widthRatio = Float.parseFloat(ratio.substring(0, separatorIndex));
                heightRatio = Float.parseFloat(ratio.substring(separatorIndex + 1));
            } catch (Exception e) {
                isRatioValid = false;
                error = e;
            }
        }
        
        if (!isRatioValid) {
            throw new IllegalArgumentException("The given ratio must be of the form 'w:h' where 'w' and 'h' are numbers, but got: " + ratio, error);
        }
        return new FloatFloatPair(widthRatio, heightRatio);
    }
    
}
