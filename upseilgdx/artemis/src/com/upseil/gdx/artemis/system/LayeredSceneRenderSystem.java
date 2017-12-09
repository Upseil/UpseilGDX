package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.Aspect.Builder;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.upseil.gdx.artemis.component.Scene;
import com.upseil.gdx.util.RequiresResize;

public class LayeredSceneRenderSystem extends LayeredIteratingSystem implements RequiresResize {
    
    protected ComponentMapper<Scene> sceneMapper;
    
    private final Batch globalBatch;

    private int screenWidth;
    private int screenHeight;
    private boolean resized;
    
    public LayeredSceneRenderSystem() {
        this(Aspect.all(Scene.class));
    }

    public LayeredSceneRenderSystem(Builder aspect) {
        super(aspect);
        globalBatch = new SpriteBatch();
    }

    @Override
    protected void process(int entityId) {
        Scene scene = sceneMapper.get(entityId);
        
        if (resized) {
            scene.update(screenWidth, screenHeight, true);
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
    
    public Batch getGlobalBatch() {
        return globalBatch;
    }
    
    @Override
    protected void dispose() {
        globalBatch.dispose();
    }
    
}
