package com.upseil.gdx.box2d.action;

import com.artemis.ComponentMapper;
import com.upseil.gdx.box2d.component.PostSolveContact;
import com.upseil.gdx.box2d.event.ContactEventHandler;
import com.upseil.gdx.box2d.event.PostSolveEvent;

public class FirePostSolveEvent extends FireContactEvent<PostSolveEvent, FirePostSolveEvent> {
    
    private ComponentMapper<PostSolveContact> mapper;
    
    public void setMapper(ComponentMapper<PostSolveContact> mapper) {
        this.mapper = mapper;
    }

    @Override
    protected ContactEventHandler<PostSolveEvent> getEventHandler() {
        return mapper.get(getState().getSelfId());
    }
    
    @Override
    public void reset() {
        mapper = null;
    }
    
}
