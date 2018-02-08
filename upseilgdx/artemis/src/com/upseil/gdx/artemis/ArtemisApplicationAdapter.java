package com.upseil.gdx.artemis;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.AbstractApplicationAdapter;
import com.upseil.gdx.util.RequiresResize;

public abstract class ArtemisApplicationAdapter extends AbstractApplicationAdapter {
    
    private World world;
    private final Array<RequiresResize> systemsToResize;
    
    public ArtemisApplicationAdapter() {
        systemsToResize = new Array<>();
    }
    
    @Override
    public void create() {
        setupWorldCreation();
        resetWorld();
    }

    protected void setupWorldCreation() { }
    
    protected abstract World createWorld();
    
    public void reset() {
        world.dispose();
        resetWorld();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void resetWorld() {
        world = createWorld();
        ImmutableBag<BaseSystem> systems = world.getSystems();
        for (int i = 0; i < systems.size(); i++) {
            BaseSystem system = systems.get(i);
            if (system instanceof RequiresResize) {
                systemsToResize.add((RequiresResize) system);
            }
        }
    }

    @Override
    protected void render(float deltaTime) {
        world.setDelta(deltaTime);
        world.process();
    }
    
    @Override
    public void resize(int width, int height) {
        for (RequiresResize system : systemsToResize) {
            system.resize(width, height);
        }
    }
    
    public World getWorld() {
        return world;
    }
    
}
