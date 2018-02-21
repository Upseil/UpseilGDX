package com.upseil.gdx.artemis.event;

import com.upseil.gdx.event.AbstractEvent;
import com.upseil.gdx.event.EventType;

public class ResizeEvent extends AbstractEvent<ResizeEvent> {
    
    public static EventType<ResizeEvent> Type = new EventType<>("Resize");
    
    private int newWidth;
    private int newHeight;
    
    public ResizeEvent set(int newWidth, int newHeight) {
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        return this;
    }

    public int getNewWidth() {
        return newWidth;
    }

    public int getNewHeight() {
        return newHeight;
    }

    @Override
    protected void setType() {
        this.type = Type;
    }
    
    @Override
    public void reset() {
        super.reset();
        newWidth = 0;
        newHeight = 0;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append(" - New Width: ").append(newWidth).append(" | New Height: ").append(newHeight);
        return builder.toString();
    }
    
}
