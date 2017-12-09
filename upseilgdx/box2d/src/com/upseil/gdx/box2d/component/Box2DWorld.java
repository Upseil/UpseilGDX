package com.upseil.gdx.box2d.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class Box2DWorld extends PooledComponent implements Disposable {
    
    private World world;
    
    public Box2DWorld initialize(Vector2 gravity) {
        return initialize(gravity, true);
    }
    
    public Box2DWorld initialize(Vector2 gravity, boolean allowSleep) {
        if (world != null) {
            throw new IllegalStateException("Allready initialized");
        }
        
        world = new World(gravity, allowSleep);
        return this;
    }
    
    public World get() {
        return world;
    }
    
    public void step(float timeStep, int velocityIterations, int positionIterations) {
        world.step(timeStep, velocityIterations, positionIterations);
    }

    public Body createBody(BodyDef bodyDefinition) {
        return world.createBody(bodyDefinition);
    }
    
    @Override
    protected void reset() {
        dispose();
        world = null;
    }
    
    @Override
    public void dispose() {
        world.dispose();
    }
    
}
