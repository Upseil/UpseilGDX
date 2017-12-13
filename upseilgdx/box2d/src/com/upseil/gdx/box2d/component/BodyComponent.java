package com.upseil.gdx.box2d.component;

import com.artemis.PooledComponent;
import com.artemis.annotations.DelayedComponentRemoval;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.action.Action;
import com.upseil.gdx.utils.GDXCollections;

@DelayedComponentRemoval
public class BodyComponent extends PooledComponent {
    
    private Body body;
    private Array<Action<Body, ?>> actions;
    
    public BodyComponent() {
        actions = new Array<>(0);
    }
    
    public Body get() {
        return body;
    }

    public void set(Body body) {
        this.body = body;
    }
    
    public void act(float deltaTime) {
        for (int index = 0; index < actions.size; index++) {
            Action<Body, ?> action = actions.get(index);
            if (action.act(deltaTime) && index < actions.size) {
                int realIndex = actions.get(index) == action ? index : actions.indexOf(action, true);
                if (realIndex != -1) {
                    actions.removeIndex(realIndex);
                    action.free();
                    index--;
                }
            }
        }
    }
    
    public void addAction(Action<Body, ?> action) {
        action.setState(body);
        actions.add(action);
    }
    
    public void removeAction(Action<Body, ?> action) {
        if (actions.removeValue(action, true)) {
            action.free();
        }
    }

    public Array<Action<Body, ?>> getActions () {
        return actions;
    }

    public boolean hasActions () {
        return actions.size > 0;
    }

    public void clearActions () {
        GDXCollections.forEach(actions, Action::free);
        actions.clear();
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
    
    public float getRotation() {
        return body.getAngle() * MathUtils.radiansToDegrees;
    }

    @Override
    protected void reset() {
        clearActions();
        body.getWorld().destroyBody(body);
        body = null;
    }
    
}
