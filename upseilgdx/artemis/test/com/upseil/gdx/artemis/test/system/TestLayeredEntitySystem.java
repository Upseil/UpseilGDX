package com.upseil.gdx.artemis.test.system;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.upseil.gdx.artemis.component.Layer;
import com.upseil.gdx.artemis.system.LayeredEntitySystem;
import com.upseil.gdx.artemis.system.LayeredEntitySystem.EntityIterator;

public class TestLayeredEntitySystem {
    
    private static World world;
    private static OpenLayeredEntitySystem system;
    private static ComponentMapper<Layer> layerMapper;

    private static int oneId;
    private static int twoId;
    private static int threeId;
    
    @BeforeClass
    public static void setUpWorld() {
        system = new OpenLayeredEntitySystem();
        WorldConfiguration configuration = new WorldConfigurationBuilder().with(system).build();
        
        world = new World(configuration);
        layerMapper = world.getMapper(Layer.class);
        
        oneId = world.create();
        layerMapper.create(oneId).setZIndex(1);
        
        twoId = world.create();
        layerMapper.create(twoId).setZIndex(2);
        
        threeId = world.create();
        layerMapper.create(threeId).setZIndex(3);
        
        world.setDelta(0.1f);
        world.process();
    }
    
    @Test
    public void testIterator() {
        int counter = 0;
        int expectedZIndex = 1;
        
        EntityIterator iterator = system.iterator();
        while (iterator.hasNext()) {
            int id = iterator.next();
            assertThat(layerMapper.get(id).getZIndex(), is(expectedZIndex));
            expectedZIndex++;
            counter++;
        }
        
        assertThat(counter, is(3));
    }
    
    @Test
    public void testReverseIterator() {
        int counter = 0;
        int expectedZIndex = 3;
        
        EntityIterator iterator = system.reverseIterator();
        while (iterator.hasNext()) {
            int id = iterator.next();
            assertThat(layerMapper.get(id).getZIndex(), is(expectedZIndex));
            expectedZIndex--;
            counter++;
        }
        
        assertThat(counter, is(3));
    }
    
    private static class OpenLayeredEntitySystem extends LayeredEntitySystem {

        public OpenLayeredEntitySystem() {
            super(Aspect.all());
        }

        @Override
        protected void processSystem() { }
        
        public EntityIterator iterator() {
            return super.iterator();
        }
        
        public EntityIterator reverseIterator() {
            return super.reverseIterator();
        }
        
    }
    
}
