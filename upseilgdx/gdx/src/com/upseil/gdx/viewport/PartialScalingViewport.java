package com.upseil.gdx.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

public class PartialScalingViewport extends PartialViewport {
    
    private Scaling scaling;

    public PartialScalingViewport (ScreenDivider divider, Scaling scaling, float worldWidth, float worldHeight) {
        this(divider, scaling, worldWidth, worldHeight, new OrthographicCamera());
    }

    public PartialScalingViewport(ScreenDivider divider, Scaling scaling, float worldWidth, float worldHeight, Camera camera) {
        super(divider, worldWidth, worldHeight, camera);
        this.scaling = scaling;
    }
    
    @Override
    protected Vector2 calculateViewportSize(Rectangle screenPart) {
        return scaling.apply(getWorldWidth(), getWorldHeight(), screenPart.getWidth(), screenPart.getHeight());
    }

    public Scaling getScaling() {
        return scaling;
    }

    public void setScaling(Scaling scaling) {
        this.scaling = scaling;
    }
    
}
