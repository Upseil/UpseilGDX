package com.upseil.gdx.artemis.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.upseil.gdx.artemis.component.InputHandler;

public class LayeredInputSystem extends LayeredEntitySystem implements InputProcessor {
    
    private ComponentMapper<InputHandler> inputHandlerMapper;

    public LayeredInputSystem() {
        super(Aspect.all(InputHandler.class));
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        EntityIterator iterator = reverseIterator();
        while (iterator.hasNext()) {
            InputHandler handler = inputHandlerMapper.get(iterator.next());
            if (handler.getProcessor().keyDown(keycode) || handler.isModal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        EntityIterator iterator = reverseIterator();
        while (iterator.hasNext()) {
            InputHandler handler = inputHandlerMapper.get(iterator.next());
            if (handler.getProcessor().keyUp(keycode) || handler.isModal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        EntityIterator iterator = reverseIterator();
        while (iterator.hasNext()) {
            InputHandler handler = inputHandlerMapper.get(iterator.next());
            if (handler.getProcessor().keyTyped(character) || handler.isModal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        EntityIterator iterator = reverseIterator();
        while (iterator.hasNext()) {
            InputHandler handler = inputHandlerMapper.get(iterator.next());
            if (handler.getProcessor().touchDown(screenX, screenY, pointer, button) || handler.isModal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        EntityIterator iterator = reverseIterator();
        while (iterator.hasNext()) {
            InputHandler handler = inputHandlerMapper.get(iterator.next());
            if (handler.getProcessor().touchUp(screenX, screenY, pointer, button) || handler.isModal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        EntityIterator iterator = reverseIterator();
        while (iterator.hasNext()) {
            InputHandler handler = inputHandlerMapper.get(iterator.next());
            if (handler.getProcessor().touchDragged(screenX, screenY, pointer) || handler.isModal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        EntityIterator iterator = reverseIterator();
        while (iterator.hasNext()) {
            InputHandler handler = inputHandlerMapper.get(iterator.next());
            if (handler.getProcessor().mouseMoved(screenX, screenY) || handler.isModal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        EntityIterator iterator = reverseIterator();
        while (iterator.hasNext()) {
            InputHandler handler = inputHandlerMapper.get(iterator.next());
            if (handler.getProcessor().scrolled(amount) || handler.isModal()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected boolean checkProcessing() {
        return false;
    }

    @Override
    protected void processSystem() { }
    
}
