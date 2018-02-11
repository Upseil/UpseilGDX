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

public class AbstractDialog extends Dialog {

    public static final int Left = 0;
    public static final int Above = 1;
    public static final int Right = 2;
    public static final int Below = 3;
    
    protected static final Object notNull = new Object();
    
    private Actor anchor;
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
    
    public void leftFrom(Actor anchor, float spacing) {
        setAnchor(anchor, spacing);
        position = Left;
    }
    
    public void above(Actor anchor, float spacing) {
        setAnchor(anchor, spacing);
        position = Above;
    }
    
    public void rightFrom(Actor anchor, float spacing) {
        setAnchor(anchor, spacing);
        position = Right;
    }
    
    public void below(Actor anchor, float spacing) {
        setAnchor(anchor, spacing);
        position = Below;
    }
    
    public void positionRelativeTo(Actor anchor, float spacing, int position) {
        setAnchor(anchor, spacing);
        this.position = position;
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
        super.show(stage, action);
        Action positioningAction;
        if (anchor == null) {
            positioningAction = Actions.action(CenterOnDisplayBoundsAction.class);
        } else {
            CenterRelativeToActorAction relativeAction = Actions.action(CenterRelativeToActorAction.class);
            relativeAction.setTarget(anchor);
            relativeAction.keepInStageBounds().spacing(anchorSpacing);
            if (position == Left) relativeAction.left();
            if (position == Above) relativeAction.top();
            if (position == Right) relativeAction.right();
            if (position == Below) relativeAction.bottom();
            positioningAction = relativeAction;
        }
        addAction(positioningAction);
        return this;
    }
    
    public void hide() {
        hide(Actions.fadeOut(0.4f, Interpolation.fade));
    }
    
    public void hide(Action action) {
        super.hide(action);
    }
    
}
