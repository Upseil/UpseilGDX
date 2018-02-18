package com.upseil.gdx.scene2d;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;

public class AugmentedProgressBar extends Stack implements Disableable {
    
    protected final ProgressBar progressBar;
    protected final Table augmentationTable;

    public AugmentedProgressBar (float min, float max, float stepSize, boolean vertical, Skin skin) {
        this(min, max, stepSize, vertical, skin.get("default-" + (vertical ? "vertical" : "horizontal"), ProgressBarStyle.class));
        augmentationTable.setSkin(skin);
    }

    public AugmentedProgressBar (float min, float max, float stepSize, boolean vertical, Skin skin, String styleName) {
        this(min, max, stepSize, vertical, skin.get(styleName, ProgressBarStyle.class));
        augmentationTable.setSkin(skin);
    }

    public AugmentedProgressBar (float min, float max, float stepSize, boolean vertical, ProgressBarStyle style) {
        progressBar = new ProgressBar(min, max, stepSize, vertical, style);
        augmentationTable = new Table();
        
        add(progressBar);
        add(augmentationTable);
    }
    
    public float getValue() {
        return progressBar.getValue();
    }
    
    public float getPercent() {
        return progressBar.getPercent();
    }
    
    public boolean setValue(float value) {
        return progressBar.setValue(value);
    }

    public float getMinValue () {
        return progressBar.getMinValue();
    }

    public float getMaxValue () {
        return progressBar.getMaxValue();
    }

    public void setRange (float min, float max) {
        progressBar.setRange(min, max);
    }
    
    public float getStepSize() {
        return progressBar.getStepSize();
    }

    public void setStepSize (float stepSize) {
        progressBar.setStepSize(stepSize);
    }

    public void setAnimateDuration (float duration) {
        progressBar.setAnimateDuration(duration);
    }
    
    public void setAnimateInterpolation (Interpolation animateInterpolation) {
        progressBar.setAnimateInterpolation(animateInterpolation);
    }
    
    public void setVisualInterpolation (Interpolation interpolation) {
        progressBar.setVisualInterpolation(interpolation);
    }

    public void setRound (boolean round) {
        progressBar.setRound(round);
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        progressBar.setDisabled(isDisabled);
    }

    @Override
    public boolean isDisabled() {
        return progressBar.isDisabled();
    }
    
    public boolean isVertical() {
        return progressBar.isVertical();
    }
    
    public ProgressBar getProgressBar() {
        return progressBar;
    }
    
    public Table getAugmentationTable() {
        return augmentationTable;
    }
    
}
