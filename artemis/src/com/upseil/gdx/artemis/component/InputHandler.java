package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.InputProcessor;

public class InputHandler extends PooledComponent {
    
    private InputProcessor processor;
    private boolean isModal;
    
    public InputProcessor getProcessor() {
        return processor;
    }

    public InputHandler setProcessor(InputProcessor processor) {
        this.processor = processor;
        return this;
    }

    public boolean isModal() {
        return isModal;
    }

    public InputHandler setModal(boolean isModal) {
        this.isModal = isModal;
        return this;
    }

    @Override
    protected void reset() {
        processor = null;
        isModal = false;
    }
    
}
