package com.upseil.gdx.test.action;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestAction {
    
    @Test
    public void testReset() {
        CountingAction action = new CountingAction();
        action.set(2, 1);
        assertThat(action.getState(), is(2));
        action.act(0);
        assertThat(action.getState(), is(3));
        action.reset();
        assertThat(action.getState(), nullValue());
    }
    
}
