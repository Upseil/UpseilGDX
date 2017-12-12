package com.upseil.gdx.scene2d.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class TemporalSequenceAction extends Action {

    private final Array<TemporalAction> actions = new Array<>(4);
    private boolean complete;
    private int index;

    public TemporalSequenceAction() {
    }

    public TemporalSequenceAction(TemporalAction action1) {
        addAction(action1);
    }

    public TemporalSequenceAction(TemporalAction action1, TemporalAction action2) {
        addAction(action1);
        addAction(action2);
    }

    public TemporalSequenceAction(TemporalAction action1, TemporalAction action2, TemporalAction action3) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
    }

    public TemporalSequenceAction(TemporalAction action1, TemporalAction action2, TemporalAction action3, TemporalAction action4) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
    }

    public TemporalSequenceAction(TemporalAction action1, TemporalAction action2, TemporalAction action3, TemporalAction action4, TemporalAction action5) {
        addAction(action1);
        addAction(action2);
        addAction(action3);
        addAction(action4);
        addAction(action5);
    }

    public boolean act (float delta) {
        if (index >= actions.size) return true;
        
        Pool<?> pool = getPool();
        setPool(null); // Ensure this action can't be returned to the pool while executing
        try {
            TemporalAction action;
            float actionDelta = delta;
            boolean actionComplete;
            do {
                action = actions.get(index);
                actionComplete = action.act(actionDelta);
                if (actionComplete) {
                    if (actor == null) return true; // This action was removed.
                    
                    index++;
                    if (index >= actions.size) {
                        complete = true;
                    } else {
                        actionDelta = action.getTime() - action.getDuration();
                    }
                }
            } while (actionComplete && !complete);
            return complete;
        } finally {
            setPool(pool);
        }
    }

    public void restart () {
        complete = false;
        index = 0;
        for (int i = 0, n = actions.size; i < n; i++) {
            actions.get(i).restart();
        }
    }

    public void reset () {
        super.reset();
        actions.clear();
    }
    
    public void addAction(Action action) {
        TemporalActionWrapper wrapper = Actions.action(TemporalActionWrapper.class);
        wrapper.setAction(action);
        addAction(wrapper);
    }

    public void addAction (TemporalAction action) {
        actions.add(action);
        if (actor != null) action.setActor(actor);
    }

    public void setActor (Actor actor) {
        for (int i = 0, n = actions.size; i < n; i++) {
            actions.get(i).setActor(actor);
        }
        super.setActor(actor);
    }

    public Array<TemporalAction> getActions () {
        return actions;
    }

    public String toString () {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append(super.toString());
        buffer.append('(');
        for (int i = 0, n = actions.size; i < n; i++) {
            if (i > 0) buffer.append(", ");
            buffer.append(actions.get(i));
        }
        buffer.append(')');
        return buffer.toString();
    }
    
    public static class TemporalActionWrapper extends TemporalAction {
        
        private Action action;
        
        public TemporalActionWrapper() {
            super(0);
        }
        
        @Override
        public void setDuration(float duration) {
        }

        @Override
        protected void update(float percent) {
            action.act(percent);
        }
        
        public void setAction (Action action) {
            this.action = action;
        }

        public Action getAction () {
            return action;
        }

        public void restart () {
            super.restart();
            if (action != null) action.restart();
        }

        public void reset () {
            super.reset();
            action = null;
        }

        public void setActor (Actor actor) {
            if (action != null) action.setActor(actor);
            super.setActor(actor);
        }

        public void setTarget (Actor target) {
            if (action != null) action.setTarget(target);
            super.setTarget(target);
        }

        public String toString () {
            return super.toString() + (action == null ? "" : "(" + action + ")");
        }
        
    }
    
}
