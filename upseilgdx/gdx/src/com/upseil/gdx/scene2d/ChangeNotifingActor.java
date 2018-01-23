package com.upseil.gdx.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ChangeNotifingActor extends Actor {

    @Override
    public void setOriginX(float originX) {
        if (getOriginX() != originX) {
            super.setOriginX(originX);
            originChanged();
        }
    }

    @Override
    public void setOriginY(float originY) {
        if (getOriginY() != originY) {
            super.setOriginY(originY);
            originChanged();
        }
    }

    @Override
    public void setOrigin(float originX, float originY) {
        if (getOriginX() != originX || getOriginY() != originY) {
            super.setOrigin(originX, originY);
        }
    }

    @Override
    public void setOrigin(int alignment) {
        float oldOriginX = getOriginX();
        float oldOriginY = getOriginY();
        super.setOrigin(alignment);
        if (oldOriginX != getOriginX() || oldOriginY != getOriginY()) {
            originChanged();
        }
    }

    protected void originChanged() {
    }

    @Override
    public void setScaleX(float scaleX) {
        if (getScaleX() != scaleX) {
            super.setScaleX(scaleX);
            scaleChanged();
        }
    }

    @Override
    public void setScaleY(float scaleY) {
        if (getScaleY() != scaleY) {
            super.setScaleY(scaleY);
            scaleChanged();
        }
    }

    @Override
    public void setScale(float scaleXY) {
        if (getScaleX() != scaleXY || getScaleY() != scaleXY) {
            super.setScale(scaleXY);
            scaleChanged();
        }
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        if (getScaleX() != scaleX || getScaleY() != scaleY) {
            super.setScale(scaleX, scaleY);
            scaleChanged();
        }
    }

    @Override
    public void scaleBy(float scale) {
        if (scale != 0) {
            super.scaleBy(scale);
            scaleChanged();
        }
    }

    @Override
    public void scaleBy(float scaleX, float scaleY) {
        if (scaleX != 0 || scaleY != 0) {
            super.scaleBy(scaleX, scaleY);
            scaleChanged();
        }
    }

    protected void scaleChanged() {
    }

    @Override
    public void setColor(Color color) {
        if (!getColor().equals(color)) {
            super.setColor(color);
            colorChanged();
        }
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        boolean changed = getColor().toIntBits() != Color.toIntBits((int) (255 * r), (int) (255 * g), (int) (255 * b), (int) (255 * a));
        if (changed) {
            super.setColor(r, g, b, a);
            colorChanged();
        }
    }

    protected void colorChanged() {
    }
    
}
