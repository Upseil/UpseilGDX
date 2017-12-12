package com.upseil.gdx.action;

import com.artemis.ComponentMapper;
import com.upseil.gdx.box2d.component.OnEndContact;
import com.upseil.gdx.box2d.event.ContactEventHandler;
import com.upseil.gdx.box2d.event.SimpleContactEvent;

public class FireEndContactEvent extends FireContactEvent<SimpleContactEvent, FireEndContactEvent> {
    
    private ComponentMapper<OnEndContact> mapper;
    
    public void setMapper(ComponentMapper<OnEndContact> mapper) {
        this.mapper = mapper;
    }

    @Override
    protected ContactEventHandler<SimpleContactEvent> getEventHandler() {
        return mapper.get(getContext().getSelfId());
    }
    
    @Override
    public void reset() {
        mapper = null;
    }
    
}
