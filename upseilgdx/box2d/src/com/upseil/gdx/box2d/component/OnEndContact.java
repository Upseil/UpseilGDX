package com.upseil.gdx.box2d.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.box2d.event.ContactEventHandler;
import com.upseil.gdx.box2d.event.SimpleContactEvent;
import com.upseil.gdx.util.GDXCollections;
import com.upseil.gdx.util.function.BooleanFunction;

public class OnEndContact extends PooledComponent implements ContactEventHandler<SimpleContactEvent> {
    
    private final Array<ContactEventHandler<SimpleContactEvent>> reactions;
    
    public OnEndContact() {
        reactions = new Array<>(4);
    }
    
    public void add(ContactEventHandler<SimpleContactEvent> reaction) {
        reactions.add(reaction);
    }
    
    public void add(BooleanFunction<SimpleContactEvent> condition, ContactEventHandler<SimpleContactEvent> reaction) {
        reactions.add(reaction.withCondition(condition));
    }
    
    @Override
    public void accept(SimpleContactEvent context) {
        GDXCollections.forEach(reactions, reaction -> reaction.accept(context));
    }
    
    @Override
    protected void reset() {
        reactions.clear();
    }
    
}
