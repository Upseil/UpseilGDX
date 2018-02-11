package com.upseil.gdx.scene2d.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CenterRelativeToActorAction extends Action {
    
    private static final Vector2 tempVector = new Vector2();
    
    private int xDirection;
    private int yDirection;
    
    private float minSpacing;
    private float maxSpacing;
    
    private Rectangle bounds = new Rectangle(-1, -1, -1, -1);
    private boolean keepInDisplayBounds;
    private boolean keepInStageBounds;
    
    private float x;
    private float y;
    private float width;
    private float height;
    
    private float targetX;
    private float targetY;
    private float targetWidth;
    private float targetHeight;
    
    private void prepare() {
        if (keepInDisplayBounds) {
            bounds.set(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        if (keepInStageBounds) {
            Viewport viewport = actor.getStage().getViewport();
            bounds.set(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        }
        
        width = actor.getWidth();
        height = actor.getHeight();
        
        target.localToStageCoordinates(tempVector.set(0, 0));
        targetX = tempVector.x;
        targetY = tempVector.y;
        targetWidth = target.getWidth();
        targetHeight = target.getHeight();
    }
    
    @Override
    public boolean act(float delta) {
        prepare();
        
        float widthAdjustment = xDirection == 0 ? (targetWidth - width) / 2 : 0;
        float heightAdjustment = yDirection == 0 ? (targetHeight - height) / 2 : 0;
        float deltaX = maxSpacing + (xDirection > 0 ? targetWidth : width);
        float deltaY = maxSpacing + (yDirection > 0 ? targetHeight : height);
        
        x = targetX + widthAdjustment + xDirection * deltaX;
        y = targetY + heightAdjustment + yDirection * deltaY;
        keepInBounds();
        
        actor.setPosition(Math.round(x), Math.round(y));
        return false;
    }

    private void keepInBounds() {
        if (bounds.x < 0 || bounds.y < 0 || bounds.width < 0 || bounds.height < 0) {
            return;
        }
        
        float actorMaxX = x + width;
        float actorMaxY = y + height;
        float boundsMaxX = bounds.x + bounds.width;
        float boundsMaxY = bounds.y + bounds.height;
        if (x < bounds.x) x += bounds.x - x;
        if (actorMaxX > boundsMaxX) x += boundsMaxX - actorMaxX;
        if (y < bounds.y) y += bounds.y - y;
        if (actorMaxY > boundsMaxY) y += boundsMaxY - actorMaxY;
        
        if (overlapsTarget()) shiftAwayFromTarget();
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
    
    public CenterRelativeToActorAction keepInBounds(Rectangle bounds) {
        this.bounds.set(bounds);
        keepInDisplayBounds = false;
        keepInStageBounds = false;
        return this;
    }

    public CenterRelativeToActorAction keepInBounds(float x, float y, float width, float height) {
        bounds.set(x, y, width, height);
        keepInDisplayBounds = false;
        keepInStageBounds = false;
        return this;
    }

    public CenterRelativeToActorAction keepInDisplayBounds() {
        keepInDisplayBounds = true;
        keepInStageBounds = false;
        return this;
    }

    public CenterRelativeToActorAction keepInStageBounds() {
        keepInDisplayBounds = false;
        keepInStageBounds = true;
        return this;
    }
    
    @Override
    public void reset() {
        super.reset();
        xDirection = 0;
        yDirection = 0;
        minSpacing = 0;
        maxSpacing = 0;
        bounds.set(-1, -1, -1, -1);
        keepInDisplayBounds = false;
        keepInStageBounds = false;
    }
    
}
