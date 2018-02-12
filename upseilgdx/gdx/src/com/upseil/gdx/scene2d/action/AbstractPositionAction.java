package com.upseil.gdx.scene2d.action;

import java.util.function.Supplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class AbstractPositionAction extends Action {
    
    protected static final Vector2 tempVector = new Vector2();
    
    private Supplier<Rectangle> boundsProvider;
    protected final Rectangle bounds = new Rectangle(-1, -1, -1, -1);
    private boolean keepInDisplayBounds;
    private boolean keepInStageBounds;
    
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    
    // TODO Only act if necessary
    @Override
    public boolean act(float delta) {
        prepareAct(delta);
        calculatePosition(delta);
        keepPositionInBounds(delta);
        actor.setPosition(Math.round(x), Math.round(y));
        return false;
    }

    protected void prepareAct(float delta) {
        if (boundsProvider != null) {
            bounds.set(boundsProvider.get());
        }
        if (keepInDisplayBounds) {
            bounds.set(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        if (keepInStageBounds) {
            Viewport viewport = actor.getStage().getViewport();
            bounds.set(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        }

        width = actor.getWidth();
        height = actor.getHeight();
    }

    protected abstract void calculatePosition(float delta);
    
    protected boolean keepPositionInBounds(float delta) {
        if (bounds.x < 0 || bounds.y < 0 || bounds.width < 0 || bounds.height < 0) {
            return false;
        }
        
        float actorMaxX = x + width;
        float actorMaxY = y + height;
        float boundsMaxX = bounds.x + bounds.width;
        float boundsMaxY = bounds.y + bounds.height;
        if (x < bounds.x) x += bounds.x - x;
        if (actorMaxX > boundsMaxX) x += boundsMaxX - actorMaxX;
        if (y < bounds.y) y += bounds.y - y;
        if (actorMaxY > boundsMaxY) y += boundsMaxY - actorMaxY;
        return true;
    }
    
    public AbstractPositionAction keepInBounds(Rectangle bounds) {
        this.bounds.set(bounds);
        boundsProvider = null;
        keepInDisplayBounds = false;
        keepInStageBounds = false;
        return this;
    }

    public AbstractPositionAction keepInBounds(float x, float y, float width, float height) {
        bounds.set(x, y, width, height);
        boundsProvider = null;
        keepInDisplayBounds = false;
        keepInStageBounds = false;
        return this;
    }

    public AbstractPositionAction keepInDisplayBounds() {
        boundsProvider = null;
        keepInDisplayBounds = true;
        keepInStageBounds = false;
        return this;
    }

    public AbstractPositionAction keepInStageBounds() {
        boundsProvider = null;
        keepInDisplayBounds = false;
        keepInStageBounds = true;
        return this;
    }
    
    public AbstractPositionAction keepInBounds(Supplier<Rectangle> boundsProvider) {
        this.boundsProvider = boundsProvider;
        keepInDisplayBounds = false;
        keepInStageBounds = false;
        return this;
    }
    
    @Override
    public void reset() {
        super.reset();
        boundsProvider = null;
        bounds.set(-1, -1, -1, -1);
        keepInDisplayBounds = false;
        keepInStageBounds = false;
    }
    
}
