package com.upseil.gdx.box2d.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Box2DDebugRenderer extends BaseSystem {
    
    private final com.badlogic.gdx.physics.box2d.Box2DDebugRenderer renderer;
    
    private World world;
    private Viewport viewport;
    
    public Box2DDebugRenderer() {
        renderer = new com.badlogic.gdx.physics.box2d.Box2DDebugRenderer();
    }
    
    public void setup(World world, Viewport viewport) {
        this.world = world;
        this.viewport = viewport;
    }
    
    @Override
    protected boolean checkProcessing() {
        return world != null;
    }
    
    @Override
    protected void processSystem() {
        viewport.apply();
        Camera camera = viewport.getCamera();
        camera.update();
        
        renderer.render(world, camera.combined);
    }
    
    @Override
    protected void dispose() {
        renderer.dispose();
    }
    
}
