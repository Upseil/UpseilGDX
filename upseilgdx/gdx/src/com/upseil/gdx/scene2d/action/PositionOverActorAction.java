package com.upseil.gdx.scene2d.action;

import java.util.function.Supplier;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

public class PositionOverActorAction extends AbstractTargetedPositionAction {
    
    private int alignment = Align.center;
    
    @Override
    protected void calculatePosition(float delta) {
        if ((alignment & Align.left) != 0) {
            x = targetX;
        } else if ((alignment & Align.right) != 0) {
            x = targetX + targetWidth - width;
        } else {
            x = targetX + (targetWidth - width) / 2;
        }

        if ((alignment & Align.bottom) != 0) {
            y = targetY;
        } else if ((alignment & Align.top) != 0) {
            y = targetY + targetHeight - height;
        } else {
            y = targetY + (targetHeight - height) / 2;
        }
    }
    
    public PositionOverActorAction setAlignment(int alignment) {
        this.alignment = alignment;
        return this;
    }
    
    public PositionOverActorAction center() {
        alignment = Align.center;
        return this;
    }
    
    public PositionOverActorAction top() {
        alignment = (alignment | Align.top) & ~Align.bottom;
        return this;
    }
    
    public PositionOverActorAction left() {
        alignment = (alignment | Align.left) & ~Align.right;
        return this;
    }
    
    public PositionOverActorAction bottom() {
        alignment = (alignment | Align.bottom) & ~Align.top;
        return this;
    }
    
    public PositionOverActorAction right() {
        alignment = (alignment | Align.right) & ~Align.left;
        return this;
    }
    
    @Override
    public PositionOverActorAction keepInBounds(Rectangle bounds) {
        super.keepInBounds(bounds);
        return this;
    }

    @Override
    public PositionOverActorAction keepInBounds(float x, float y, float width, float height) {
        super.keepInBounds(x, y, width, height);
        return this;
    }

    @Override
    public PositionOverActorAction keepInDisplayBounds() {
        super.keepInDisplayBounds();
        return this;
    }

    @Override
    public PositionOverActorAction keepInStageBounds() {
        super.keepInStageBounds();
        return this;
    }

    @Override
    public PositionOverActorAction keepInBounds(Supplier<Rectangle> boundsProvider) {
        super.keepInBounds(boundsProvider);
        return this;
    }
    
    @Override
    public void reset() {
        super.reset();
        alignment = Align.center;
    }
    
}
