package com.upseil.gdx.box2d.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.box2d.ContactReaction;
import com.upseil.gdx.util.function.BooleanFunction;
import com.upseil.gdx.utils.GDXCollections;

public class OnBeginContact extends PooledComponent implements ContactReaction {
    
    private final Array<ContactReaction> reactions;
    
    public OnBeginContact() {
        reactions = new Array<>(4);
    }
    
    public void add(ContactReaction reaction) {
        reactions.add(reaction);
    }
    
    public void add(BooleanFunction<ContactReaction.Context> condition, ContactReaction reaction) {
        add(reaction.withCondition(condition));
    }
    
    @Override
    public void accept(Context context) {
        GDXCollections.forEach(reactions, reaction -> reaction.accept(context));
    }
    
    @Override
    protected void reset() {
        reactions.clear();
    }
    
}
