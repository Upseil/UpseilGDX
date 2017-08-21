package com.upseil.gdx.artemis.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class ClearScreenSystem extends BaseSystem {
    
    @Override
    protected void processSystem() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
}
