package com.upseil.gdx.artemis.system;

import com.artemis.BaseSystem;
import com.artemis.SystemInvocationStrategy;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.utils.Array;

public class DiscreteInvocationStrategy extends SystemInvocationStrategy {
    
    private final float frameTimeInSeconds;
    private final int frameTimeInMilliseconds;
    private final float maximumLogicActionsPerSecond;
    
    private final Array<SystemHandle> logicalSystems;
    private final Array<SystemHandle> visualSystems;
    
    private float logicalDelta;
    private float additionalDelta;
    private boolean isAdditionalDeltaConsumed;
    
    private boolean isPaused;
    
    public DiscreteInvocationStrategy(float frameTimeInSeconds) {
        super();
        this.frameTimeInSeconds = frameTimeInSeconds;
        frameTimeInMilliseconds = (int) (frameTimeInSeconds * 1000);
        maximumLogicActionsPerSecond = (1f / frameTimeInSeconds) * 0.5f;
        
        logicalSystems = new Array<>();
        visualSystems = new Array<>();
    }
    
    @Override
    protected final void initialize() {
        ImmutableBag<BaseSystem> systems = world.getSystems();
        for (int i = 0; i < systems.size(); i++) {
            BaseSystem system = systems.get(i);
            SystemHandle handle = new SystemHandle(system, i);
            
            if (system instanceof LogicalSystem) {
                logicalSystems.add(handle);
            } else {
                visualSystems.add(handle);
            }
        }
        
        customInitialize();
    }

    protected void customInitialize() { }

    @Override
    protected void process() {
        float visualDelta = logicalDelta + world.getDelta() + additionalDelta;
        logicalDelta += (isPaused ? 0 : world.getDelta()) + additionalDelta;
        isAdditionalDeltaConsumed = true;
        
        beforeSystemProcessing();
        
        world.setDelta(Math.min(logicalDelta, frameTimeInSeconds));
        do {
            logicalDelta -= frameTimeInSeconds;
            process(logicalSystems);
            if (abortLogicProcessing()) {
                onAbortLogicProcessing();
                logicalDelta = 0;
            }
        } while (logicalDelta > frameTimeInSeconds);
        logicalDelta = logicalDelta < 0 ? 0 : logicalDelta;
        
        visualDelta -= logicalDelta;
        world.setDelta(visualDelta);
        process(visualSystems);
        updateEntityStates();
        
        afterSystemProcessing();

        additionalDelta = isAdditionalDeltaConsumed ? 0 : additionalDelta;
    }

    protected void beforeSystemProcessing() { }
    
    protected boolean abortLogicProcessing() {
        return false;
    }

    protected void onAbortLogicProcessing() { }

    protected void afterSystemProcessing() { }

    private void process(Array<SystemHandle> systems) {
        for (SystemHandle handle : systems) {
            if (disabled.get(handle.disabledIndex))
                continue;

            updateEntityStates();
            handle.system.process();
        }
    }
    
    protected final float getLogicalDelta() {
        return logicalDelta;
    }
    
    protected final float getAdditionalDelta() {
        return additionalDelta;
    }
    
    protected final void setAdditionalDelta(float additionalDelta) {
        this.additionalDelta = additionalDelta;
        isAdditionalDeltaConsumed = false;
    }

    public final float getFrameTimeInSeconds() {
        return frameTimeInSeconds;
    }

    public final int getFrameTimeInMilliseconds() {
        return frameTimeInMilliseconds;
    }

    public final float getMaximumLogicActionsPerSecond() {
        return maximumLogicActionsPerSecond;
    }
    
    public final void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }
    
    public final boolean isPaused() {
        return isPaused && additionalDelta <= 0;
    }

    private class SystemHandle {
        
        private final BaseSystem system;
        private final int disabledIndex;
        
        public SystemHandle(BaseSystem system, int disabledIndex) {
            this.system = system;
            this.disabledIndex = disabledIndex;
        }
        
    }
    
}
