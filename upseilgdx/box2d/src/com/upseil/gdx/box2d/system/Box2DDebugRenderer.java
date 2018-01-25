package com.upseil.gdx.box2d.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.upseil.gdx.box2d.util.DebugRenderer;

public class Box2DDebugRenderer extends BaseSystem {
    
    private final DebugRenderer renderer;
    
    private World world;
    private Viewport viewport;
    
    public Box2DDebugRenderer() {
        renderer = new DebugRenderer();
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
    
    public DebugRenderer getRenderer() {
        return renderer;
    }
    
}
