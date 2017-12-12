package com.upseil.gdx.box2d.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.box2d.event.ContactEventHandler;
import com.upseil.gdx.box2d.event.PreSolveEvent;
import com.upseil.gdx.util.function.BooleanFunction;
import com.upseil.gdx.utils.GDXCollections;

public class PreSolveContact extends PooledComponent implements ContactEventHandler<PreSolveEvent> {
    
    private final Array<ContactEventHandler<PreSolveEvent>> reactions;
    
    public PreSolveContact() {
        reactions = new Array<>(4);
    }
    
    public void add(ContactEventHandler<PreSolveEvent> reaction) {
        reactions.add(reaction);
    }
    
    public void add(BooleanFunction<PreSolveEvent> condition, ContactEventHandler<PreSolveEvent> reaction) {
        reactions.add(reaction.withCondition(condition));
    }
    
    @Override
    public void accept(PreSolveEvent context) {
        GDXCollections.forEach(reactions, reaction -> reaction.accept(context));
    }
    
    @Override
    protected void reset() {
        reactions.clear();
    }
    
}
