package com.upseil.gdx.math;

import com.badlogic.gdx.math.Interpolation;

public class ClippedInterpolation extends Interpolation {
    
    private final Interpolation interpolation;
    private final float clippingFactor;
    private final float normalizationFactor;

    public ClippedInterpolation(Interpolation interpolation, float clippedBy) {
        this.interpolation = interpolation;
        this.clippingFactor = 1 - clippedBy;
        this.normalizationFactor = 1f / interpolation.apply(clippingFactor);
    }

    @Override
    public float apply(float a) {
        return interpolation.apply(a * clippingFactor) * normalizationFactor;
    }
    
}