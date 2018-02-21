package com.upseil.gdx.box2d.component;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.box2d.event.ContactEventHandler;
import com.upseil.gdx.box2d.event.PostSolveEvent;
import com.upseil.gdx.util.GDXCollections;
import com.upseil.gdx.util.function.BooleanFunction;

public class PostSolveContact extends PooledComponent implements ContactEventHandler<PostSolveEvent> {
    
    private final Array<ContactEventHandler<PostSolveEvent>> reactions;
    
    public PostSolveContact() {
        reactions = new Array<>(4);
    }
    
    public void add(ContactEventHandler<PostSolveEvent> reaction) {
        reactions.add(reaction);
    }
    
    public void add(BooleanFunction<PostSolveEvent> condition, ContactEventHandler<PostSolveEvent> reaction) {
        reactions.add(reaction.withCondition(condition));
    }
    
    @Override
    public void accept(PostSolveEvent context) {
        GDXCollections.forEach(reactions, reaction -> reaction.accept(context));
    }
    
    @Override
    protected void reset() {
        reactions.clear();
    }
    
}
