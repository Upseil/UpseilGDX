package com.upseil.gdx.testbed;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.util.RequiresResize;

@Wire
public abstract class ArtemisTestbedApplication extends TestbedApplication {
    
    private World world;
    private final Array<RequiresResize> systemsToResize;
    
    public ArtemisTestbedApplication() {
        systemsToResize = new Array<>();
    }
    
    @Override
    public void create() {
        setupWorldCreation();
        world = createWorld();
        ImmutableBag<BaseSystem> systems = world.getSystems();
        for (int i = 0; i < systems.size(); i++) {
            BaseSystem system = systems.get(i);
            if (system instanceof RequiresResize) {
                systemsToResize.add((RequiresResize) system);
            }
        }
        world.inject(this);
        initialize();
    }

    protected void setupWorldCreation() { }
    
    protected abstract World createWorld();
    
    protected abstract void initialize();

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

    @Override
    public void dispose() {
        world.dispose();
    }
    
}
