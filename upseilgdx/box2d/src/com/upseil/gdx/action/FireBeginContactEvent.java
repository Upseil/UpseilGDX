package com.upseil.gdx.action;

import com.artemis.ComponentMapper;
import com.upseil.gdx.box2d.component.OnBeginContact;
import com.upseil.gdx.box2d.event.ContactEventHandler;
import com.upseil.gdx.box2d.event.SimpleContactEvent;

public class FireBeginContactEvent extends FireContactEvent<SimpleContactEvent, FireBeginContactEvent> {
    
    private ComponentMapper<OnBeginContact> mapper;
    
    public void setMapper(ComponentMapper<OnBeginContact> mapper) {
        this.mapper = mapper;
    }

    @Override
    protected ContactEventHandler<SimpleContactEvent> getEventHandler() {
        return mapper.get(getState().getSelfId());
    }
    
    @Override
    public void reset() {
        mapper = null;
    }
    
}
