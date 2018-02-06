package com.upseil.gdx.box2d.component;

import com.artemis.PooledComponent;
import com.artemis.annotations.DelayedComponentRemoval;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.action.Action;
import com.upseil.gdx.util.GDXCollections;

@DelayedComponentRemoval
public class BodyComponent extends PooledComponent {
    
    private Body body;
    private final Array<Action<Body, ?>> actions;
    
    private boolean destroyBodyOnReset;
    
    public BodyComponent() {
        actions = new Array<>(0);
        destroyBodyOnReset = true;
    }
    
    public Body get() {
        return body;
    }

    public boolean destroyBodyOnReset() {
        return destroyBodyOnReset;
    }

    public void setDestroyBodyOnReset(boolean destroyBodyOnReset) {
        this.destroyBodyOnReset = destroyBodyOnReset;
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
    
    public boolean removeAction(Action<Body, ?> action) {
        if (actions.removeValue(action, true)) {
            action.free();
            return true;
        }
        return false;
    }

    public Array<Action<Body, ?>> getActions() {
        return actions;
    }

    public boolean hasActions() {
        return actions.size > 0;
    }

    public void clearActions() {
        GDXCollections.forEach(actions, Action::free);
        actions.clear();
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Vector2 getCenterOfMass() {
        return body.getLocalCenter();
    }
    
    public float getRotation() {
        return body.getAngle() * MathUtils.radiansToDegrees;
    }

    public void setCategoryBits(short categoryBits) {
        GDXCollections.forEach(body.getFixtureList(), fixture -> {
            Filter filter = fixture.getFilterData();
            filter.categoryBits = categoryBits;
            fixture.setFilterData(filter);
        });
    }

    public void setMaskBits(short maskBits) {
        GDXCollections.forEach(body.getFixtureList(), fixture -> {
            Filter filter = fixture.getFilterData();
            filter.maskBits = maskBits;
            fixture.setFilterData(filter);
        });
    }

    public void setGroupIndex(short groupIndex) {
        GDXCollections.forEach(body.getFixtureList(), fixture -> {
            Filter filter = fixture.getFilterData();
            filter.groupIndex = groupIndex;
            fixture.setFilterData(filter);
        });
    }
    
    public void setFilterData(Filter filter) {
        setFilterData(filter.categoryBits, filter.maskBits, filter.groupIndex);
    }

    public void setFilterData(short categoryBits, short maskBits, short groupIndex) {
        GDXCollections.forEach(body.getFixtureList(), fixture -> {
            Filter filter = fixture.getFilterData();
            filter.categoryBits = categoryBits;
            filter.maskBits = maskBits;
            filter.groupIndex = groupIndex;
            fixture.setFilterData(filter);
        });
    }

    @Override
    protected void reset() {
        clearActions();
        if (destroyBodyOnReset) {
            body.getWorld().destroyBody(body);
        }
        body = null;
    }
    
}
