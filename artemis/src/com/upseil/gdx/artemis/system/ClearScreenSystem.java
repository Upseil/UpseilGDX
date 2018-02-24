package com.upseil.gdx.artemis.system;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class ClearScreenSystem extends BaseSystem {
    
    private final int clearMask;
	private Color clearColor;
	
    public ClearScreenSystem() {
		this(GL20.GL_COLOR_BUFFER_BIT, Color.BLACK);
	}
    
    public ClearScreenSystem(Color clearColor) {
        this(GL20.GL_COLOR_BUFFER_BIT, clearColor);
    }

    public ClearScreenSystem(int clearMask, Color clearColor) {
        this.clearMask = clearMask;
		this.clearColor = clearColor;
	}

	@Override
    protected void processSystem() {
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, 1);
        Gdx.gl.glClear(clearMask);
    }

	public Color getClearColor() {
		return clearColor;
	}

	public void setClearColor(Color clearColor) {
		this.clearColor = clearColor;
	}
    
}
