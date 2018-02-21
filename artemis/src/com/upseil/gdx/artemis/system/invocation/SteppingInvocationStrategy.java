package com.upseil.gdx.artemis.system.invocation;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.artemis.system.RequiresStepping;

public class SteppingInvocationStrategy extends SystemInvocationStrategy {
    
    private final float stepTimeInSeconds;
    private final int stepTimeInMilliseconds;
    private final float maximumActionsPerSecond;
    
    private final Array<SystemHandle> steppingSystems;
    private final Array<SystemHandle> normalSystems;
    
    private float accumulator;
    private float additionalDelta;
    private boolean isAdditionalDeltaConsumed;
    
    private boolean isPaused;
    
    public SteppingInvocationStrategy(float stepTimeInSeconds) {
        super();
        this.stepTimeInSeconds = stepTimeInSeconds;
        stepTimeInMilliseconds = (int) (stepTimeInSeconds * 1000);
        maximumActionsPerSecond = (1f / stepTimeInSeconds) * 0.5f;
        
        steppingSystems = new Array<>();
        normalSystems = new Array<>();
    }
    
    @Override
    protected final void initialize() {
        ImmutableBag<BaseSystem> systems = world.getSystems();
        for (int i = 0; i < systems.size(); i++) {
            BaseSystem system = systems.get(i);
            SystemHandle handle = new SystemHandle(system, i);
            
            if (system instanceof RequiresStepping) {
                steppingSystems.add(handle);
            } else {
                normalSystems.add(handle);
            }
        }
        
        internalInitialize();
    }

    protected void internalInitialize() { }

    @Override
    protected void process() {
        float completeDelta = accumulator + world.getDelta() + additionalDelta;
        accumulator += (isPaused ? 0 : world.getDelta()) + additionalDelta;
        isAdditionalDeltaConsumed = true;
        
        beforeSystemProcessing();
        
        world.setDelta(Math.min(accumulator, stepTimeInSeconds));
        do {
            accumulator -= stepTimeInSeconds;
            process(steppingSystems);
            if (abortStepping()) {
                onAbortStepping();
                accumulator = 0;
            }
        } while (accumulator > stepTimeInSeconds);
        accumulator = accumulator < 0 ? 0 : accumulator;
        
        completeDelta -= accumulator;
        world.setDelta(completeDelta);
        process(normalSystems);
        updateEntityStates();
        
        afterSystemProcessing();

        additionalDelta = isAdditionalDeltaConsumed ? 0 : additionalDelta;
    }

    protected void beforeSystemProcessing() { }
    
    protected boolean abortStepping() {
        return false;
    }

    protected void onAbortStepping() { }

    protected void afterSystemProcessing() { }

    private void process(Array<SystemHandle> systems) {
        for (SystemHandle handle : systems) {
            if (disabled.get(handle.getDisabledIndex()))
                continue;

            updateEntityStates();
            handle.process();
        }
    }
    
    protected final float getAccumulator() {
        return accumulator;
    }
    
    public final float getAdditionalDelta() {
        return additionalDelta;
    }
    
    public final void setAdditionalDelta(float additionalDelta) {
        this.additionalDelta = additionalDelta;
        isAdditionalDeltaConsumed = false;
    }

    public final float getStepTimeInSeconds() {
        return stepTimeInSeconds;
    }

    public final int getStepTimeInMilliseconds() {
        return stepTimeInMilliseconds;
    }

    public final float getMaximumActionsPerSecond() {
        return maximumActionsPerSecond;
    }
    
    public final void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }
    
    public final boolean isPaused() {
        return isPaused && additionalDelta <= 0;
    }
    
}
