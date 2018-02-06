package com.upseil.gdx.artemis.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.action.Action;
import com.upseil.gdx.util.GDXCollections;

public class ActingComponent extends PooledComponent {

	private final Array<Action<?, ?>> actions;
	
	public ActingComponent() {
		actions = new Array<>(8);
	}
    
    public void act(float deltaTime) {
        for (int index = 0; index < actions.size; index++) {
            Action<?, ?> action = actions.get(index);
            if (action.act(deltaTime) && index < actions.size) {
                int realIndex = actions.get(index) == action ? index : actions.indexOf(action, true);
                if (realIndex != -1) {
                    actions.removeIndex(realIndex);
                    action.free();
                    index--;
                }
            }
        }
    }
	
	public void addAction(Action<?, ?> action) {
		actions.add(action);
	}
	
	public boolean removeAction(Action<?, ?> action) {
		if (actions.removeValue(action, true)) {
			action.free();
			return true;
		}
		return false;
	}
	
	public Array<Action<?, ?>> getActions() {
		return actions;
	}

    public void clear() {
        GDXCollections.forEach(actions, Action::free);
        actions.clear();
    }
	
	public boolean hasActions() {
		return actions.size > 0;
	}
	
	@Override
	protected void reset() {
		clear();
	}

}
