package com.upseil.gdx.scene2d.dialog;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.upseil.gdx.scene2d.action.CenterOnDisplayBoundsAction;
import com.upseil.gdx.scene2d.action.CenterRelativeToActorAction;
import com.upseil.gdx.scene2d.action.PositionOverActorAction;

public abstract class AbstractDialog extends Dialog {

    public static final int Left = 0;
    public static final int Above = 1;
    public static final int Right = 2;
    public static final int Below = 3;
    
    protected static final Object notNull = new Object();
    
    private Actor anchor;
    
    private int alignment;
    
    private float anchorSpacing;
    private int position;
    
    public AbstractDialog(String title, Skin skin, boolean isModal) {
        this(title, skin, (isModal ? "modal-" : "") + "dialog", isModal);
    }

    public AbstractDialog(String title, Skin skin, String style, boolean isModal) {
        super(title, skin, style);
        setModal(isModal);
        setKeepWithinStage(false);
        getTitleLabel().setAlignment(Align.center);
        anchor = null;
    }
    
    public void centerOnScreen() {
        anchor = null;
    }
    
    public void over(Actor anchor, int alignment) {
        setAnchor(anchor, 0);
        this.alignment = alignment;
        position = -1;
    }
    
    public void leftFrom(Actor anchor, float spacing) {
        positionRelativeTo(anchor, spacing, Left);
    }
    
    public void above(Actor anchor, float spacing) {
        positionRelativeTo(anchor, spacing, Above);
    }
    
    public void rightFrom(Actor anchor, float spacing) {
        positionRelativeTo(anchor, spacing, Right);
    }
    
    public void below(Actor anchor, float spacing) {
        positionRelativeTo(anchor, spacing, Below);
    }
    
    public void positionRelativeTo(Actor anchor, float spacing, int position) {
        setAnchor(anchor, spacing);
        this.position = position;
        alignment = -1;
    }

    private void setAnchor(Actor anchor, float spacing) {
        this.anchor = anchor;
        anchorSpacing = spacing;
    }
    
    @Override
    public AbstractDialog show(Stage stage) {
        return show(stage, Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.4f, Interpolation.fade)));
    }
    
    public AbstractDialog show(Stage stage, Action action) {
    	beforeShow();
        super.show(stage, action);
        Action positioningAction = null;
        if (anchor != null) {
            if (position != -1) {
                CenterRelativeToActorAction relativeAction = Actions.action(CenterRelativeToActorAction.class);
                relativeAction.setTarget(anchor);
                relativeAction.keepInStageBounds().spacing(anchorSpacing);
                if (position == Left) relativeAction.left();
                if (position == Above) relativeAction.top();
                if (position == Right) relativeAction.right();
                if (position == Below) relativeAction.bottom();
                positioningAction = relativeAction;
            }
            if (alignment != -1) {
                PositionOverActorAction overAction = Actions.action(PositionOverActorAction.class);
                overAction.setTarget(anchor);
                overAction.keepInStageBounds().setAlignment(alignment);
                positioningAction = overAction;
            }
        }
        positioningAction = positioningAction != null ? positioningAction : Actions.action(CenterOnDisplayBoundsAction.class);
        addAction(positioningAction);
        return this;
    }
    
    protected void beforeShow() { }

	public void hide() {
        hide(Actions.fadeOut(0.4f, Interpolation.fade));
    }
    
    public void hide(Action action) {
        super.hide(action);
    }
    
}
