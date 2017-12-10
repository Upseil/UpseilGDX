package com.upseil.gdx.box2d;

import java.util.function.Consumer;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.upseil.gdx.pool.AbstractPooled;
import com.upseil.gdx.pool.Pooled;
import com.upseil.gdx.util.function.BooleanFunction;

@FunctionalInterface
public interface ContactReaction extends Consumer<ContactReaction.Context> {
    
    default ContactReaction withCondition(BooleanFunction<ContactReaction.Context> condition) {
        return context -> { if (condition.apply(context)) this.accept(context); };
    }
    
    static ContactReaction.Context obtainContext() {
        return Pooled.get(Context.class);
    }
    
    public class Context extends AbstractPooled {
        
        private int selfId;
        private Fixture selfFixture;
        
        private int otherId;
        private Fixture otherFixture;
        
        private Contact contact;
        
        public Context intialize(int selfId, Fixture selfFixture, int otherId, Fixture otherFixture, Contact contact) {
            this.selfId = selfId;
            this.selfFixture = selfFixture;
            this.otherId = otherId;
            this.otherFixture = otherFixture;
            this.contact = contact;
            return this;
        }

        public int getSelfId() {
            return selfId;
        }

        public Fixture getSelfFixture() {
            return selfFixture;
        }

        public int getOtherId() {
            return otherId;
        }

        public Fixture getOtherFixture() {
            return otherFixture;
        }
        
        public Contact getContact() {
            return contact;
        }

        public WorldManifold getManifold() {
            return contact.getWorldManifold();
        }

        @Override
        public void reset() {
            super.reset();
            selfId = -1;
            selfFixture = null;
            otherId = -1;
            otherFixture = null;
            contact = null;
        }
        
    }
    
}
