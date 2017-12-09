package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorComponent extends PooledComponent {
    
    private Actor actor;
    
    public Actor get() {
        return actor;
    }

    public void set(Actor actor) {
        this.actor = actor;
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }
    
    public void setPosition(float x, float y) {
        actor.setPosition(x, y);
    }

    public void setRotation(float degrees) {
        actor.setRotation(degrees);
    }

    public float getWidth() {
        return actor.getWidth();
    }

    public float getHeight() {
        return actor.getHeight();
    }

    @Override
    protected void reset() {
        actor.remove();
        actor = null;
    }
    
}
