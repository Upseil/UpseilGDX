package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.upseil.gdx.util.builder.Builder;

public interface ShapeBuilderBase<T> extends Builder<T>, Disposable {
    
//- Utility methods ---------------------------------------------------------------------

    Rectangle getBounds();
    float getAngle();
    
}
