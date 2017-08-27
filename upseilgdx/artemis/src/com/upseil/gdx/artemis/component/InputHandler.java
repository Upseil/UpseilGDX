package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.InputProcessor;

public class InputHandler extends PooledComponent {
    
    private InputProcessor processor;
    private boolean isModal;
    
    public InputProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(InputProcessor processor) {
        this.processor = processor;
    }

    public boolean isModal() {
        return isModal;
    }

    public void setModal(boolean isModal) {
        this.isModal = isModal;
    }

    @Override
    protected void reset() {
        processor = null;
        isModal = false;
    }
    
}
