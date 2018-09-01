package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.annotations.SkipWire;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.upseil.gdx.artemis.component.Ignore;
import com.upseil.gdx.artemis.component.Layer;
import com.upseil.gdx.artemis.component.Scene;
import com.upseil.gdx.util.RequiresResize;

@SkipWire // Generic typing seems to break wiring in GWT
public class LayeredSceneRenderSystem<B extends Batch> extends LayeredIteratingSystem implements RequiresResize {
    
    private ComponentMapper<Scene> sceneMapper;
    
    private final B globalBatch;

    private int screenWidth;
    private int screenHeight;
    private boolean resized;
    
    @SuppressWarnings("unchecked")
    public LayeredSceneRenderSystem(B batch) {
        this(Aspect.all(Scene.class).exclude(Ignore.class), batch);
    }

    public LayeredSceneRenderSystem(Builder aspect, B batch) {
        super(aspect);
        globalBatch = batch;
    }
    
    @Override
    protected void initialize() {
        layerMapper = world.getMapper(Layer.class);
        sceneMapper = world.getMapper(Scene.class);
    }
    
    @Override
    protected void inserted(int entityId) {
        super.inserted(entityId);

        Scene scene = sceneMapper.get(entityId);
        Viewport viewport = scene.getViewport();
        if (viewport.getScreenWidth() != screenWidth || viewport.getScreenHeight() != screenHeight) {
            scene.update(screenWidth, screenHeight);
        }
    }
    
    @Override
    protected void process(int entityId) {
        Scene scene = sceneMapper.get(entityId);
        
        if (resized) {
            scene.update(screenWidth, screenHeight);
        } else {
            scene.apply();
        }
        
        scene.act(world.getDelta());
        scene.draw();
    }
    
    @Override
    protected void end() {
        resized = false;
    }

    @Override
    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        resized = true;
    }
    
    public B getGlobalBatch() {
        return  globalBatch;
    }
    
    @Override
    protected void dispose() {
        globalBatch.dispose();
    }
    
}
