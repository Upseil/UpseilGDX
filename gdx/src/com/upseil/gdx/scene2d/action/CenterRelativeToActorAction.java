package com.upseil.gdx.scene2d.action;

import java.util.function.Supplier;

import com.badlogic.gdx.math.Rectangle;

public class CenterRelativeToActorAction extends AbstractTargetedPositionAction {
    
    private int xDirection;
    private int yDirection;
    
    private float minSpacing;
    private float maxSpacing;
    
    @Override
    protected void calculatePosition(float delta) {
        float widthAdjustment = xDirection == 0 ? (targetWidth - width) / 2 : 0;
        float heightAdjustment = yDirection == 0 ? (targetHeight - height) / 2 : 0;
        float deltaX = maxSpacing + (xDirection > 0 ? targetWidth : width);
        float deltaY = maxSpacing + (yDirection > 0 ? targetHeight : height);
        
        x = targetX + widthAdjustment + xDirection * deltaX;
        y = targetY + heightAdjustment + yDirection * deltaY;
    }
    
    @Override
    protected boolean keepPositionInBounds(float delta) {
        boolean positionChanged = super.keepPositionInBounds(delta);
        if (positionChanged && overlapsTarget()) shiftAwayFromTarget();
        return positionChanged;
    }

    private boolean overlapsTarget() {
        float paddedX = x - minSpacing;
        float paddedY = y - minSpacing;
        float paddedWidth = width + 2 * minSpacing;
        float paddedHeight = height + 2 * maxSpacing;
        return paddedX < targetX + targetWidth && paddedX + paddedWidth > targetX &&
               paddedY < targetY + targetHeight && paddedY + paddedHeight > targetY;
    }

    private void shiftAwayFromTarget() {
        if (yDirection == 0) { // Shifting horizontally
            float shiftLeft = targetX - width - minSpacing;
            if (shiftLeft > bounds.x) x = shiftLeft;
            else /*shift right*/      x = targetX + targetWidth + minSpacing;
        } else { // Shifting vertically
            float shiftDown = targetY - height - minSpacing;
            if (shiftDown > bounds.y) y = shiftDown;
            else /*shift up*/         y = targetY + targetHeight + minSpacing;
        }
    }

    public CenterRelativeToActorAction top() {
        xDirection = 0;
        yDirection = 1;
        return this;
    }
    
    public CenterRelativeToActorAction bottom() {
        xDirection = 0;
        yDirection = -1;
        return this;
    }
    
    public CenterRelativeToActorAction left() {
        xDirection = -1;
        yDirection = 0;
        return this;
    }
    
    public CenterRelativeToActorAction right() {
        xDirection = 1;
        yDirection = 0;
        return this;
    }
    
    public CenterRelativeToActorAction spacing(float spacing) {
        minSpacing = spacing;
        maxSpacing = spacing;
        return this;
    }
    
    public CenterRelativeToActorAction spacing(float minSpacing, float maxSpacing) {
        this.minSpacing = minSpacing;
        this.maxSpacing = maxSpacing;
        return this;
    }
    
    @Override
    public CenterRelativeToActorAction keepInBounds(Rectangle bounds) {
        super.keepInBounds(bounds);
        return this;
    }

    @Override
    public CenterRelativeToActorAction keepInBounds(float x, float y, float width, float height) {
        super.keepInBounds(x, y, width, height);
        return this;
    }

    @Override
    public CenterRelativeToActorAction keepInDisplayBounds() {
        super.keepInDisplayBounds();
        return this;
    }

    @Override
    public CenterRelativeToActorAction keepInStageBounds() {
        super.keepInStageBounds();
        return this;
    }

    @Override
    public CenterRelativeToActorAction keepInBounds(Supplier<Rectangle> boundsProvider) {
        super.keepInBounds(boundsProvider);
        return this;
    }

    @Override
    public void reset() {
        super.reset();
        xDirection = 0;
        yDirection = 0;
        minSpacing = 0;
        maxSpacing = 0;
    }
    
}
