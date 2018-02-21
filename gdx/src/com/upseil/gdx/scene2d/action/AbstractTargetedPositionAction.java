package com.upseil.gdx.scene2d.action;

public abstract class AbstractTargetedPositionAction extends AbstractPositionAction {
    
    protected float targetX;
    protected float targetY;
    protected float targetWidth;
    protected float targetHeight;

    @Override
    protected void prepareAct(float delta) {
        super.prepareAct(delta);
        
        target.localToStageCoordinates(tempVector.set(0, 0));
        targetX = tempVector.x;
        targetY = tempVector.y;
        targetWidth = target.getWidth();
        targetHeight = target.getHeight();
    }
    
}
