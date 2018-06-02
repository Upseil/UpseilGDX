package com.upseil.gdx.test.action;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.upseil.gdx.action.InfiniteAction;
import com.upseil.gdx.action.PausableAction;

public class TestDelegateAction {
    
    @Test
    public void testInfiniteAction() {
        CountingAction innerAction = new CountingAction();
        innerAction.set(0, 1, 2);
        InfiniteAction<Integer, CountingAction> action = new InfiniteAction<>();
        action.setAction(innerAction);
        
        while (!innerAction.act(0)) { }
        assertThat(action.act(0), is(false));
    }
    
    @Test
    public void testPausableAction() {
        CountingAction innerAction = new CountingAction();
        innerAction.set(0, 1);
        PausableAction<Integer, CountingAction> action = new PausableAction<>();
        action.setAction(innerAction);
        
        action.act(0);
        assertThat(action.getState(), is(1));
        action.setActive(false);
        action.act(0);
        assertThat(action.getState(), is(1));
        action.setActive(true);
        action.act(0);
        assertThat(action.getState(), is(2));
    }
    
}
