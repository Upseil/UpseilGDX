package com.upseil.gdx.action;

import com.artemis.ComponentMapper;
import com.upseil.gdx.box2d.component.PreSolveContact;
import com.upseil.gdx.box2d.event.ContactEventHandler;
import com.upseil.gdx.box2d.event.PreSolveEvent;

public class FirePreSolveEvent extends FireContactEvent<PreSolveEvent, FirePreSolveEvent> {
    
    private ComponentMapper<PreSolveContact> mapper;
    
    public void setMapper(ComponentMapper<PreSolveContact> mapper) {
        this.mapper = mapper;
    }

    @Override
    protected ContactEventHandler<PreSolveEvent> getEventHandler() {
        return mapper.get(getState().getSelfId());
    }
    
    @Override
    public void reset() {
        mapper = null;
    }
    
}
