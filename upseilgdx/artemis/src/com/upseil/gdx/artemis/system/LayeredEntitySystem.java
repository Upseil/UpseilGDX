package com.upseil.gdx.artemis.system;

import java.util.NoSuchElementException;
import java.util.function.IntConsumer;

import com.artemis.Aspect.Builder;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.IntSet.IntSetIterator;
import com.upseil.gdx.artemis.component.Layer;

public abstract class LayeredEntitySystem extends BaseEntitySystem {
    
    protected ComponentMapper<Layer> layerMapper;

    private final IntMap<IntSet> entitiesPerLayer;
    private final IntArray layersToRender;
    private final EntityIterator iterator;
    
    @SuppressWarnings("unchecked")
    public LayeredEntitySystem(Builder aspect) {
        super(aspect.all(Layer.class));
        entitiesPerLayer = new IntMap<>();
        layersToRender = new IntArray();
        iterator = new EntityIterator();
    }
    
    @Override
    protected void inserted(int entity) {
        int zIndex = layerMapper.get(entity).getZIndex();
        IntSet entities = entitiesPerLayer.get(zIndex);
        if (entities == null) {
            entities = new IntSet();
            entitiesPerLayer.put(zIndex, entities);
            
            layersToRender.add(zIndex);
            layersToRender.sort();
        }
        entities.add(entity);
    }
    
    @Override
    protected void removed(int entity) {
        int zIndex = layerMapper.get(entity).getZIndex();
        IntSet entities = entitiesPerLayer.get(zIndex);
        if (entities != null) {
            entities.remove(entity);
        }
    }
    
    protected EntityIterator iterator() {
        iterator.reset();
        return iterator;
    }
    
    protected void forEach(IntConsumer entityConsumer) {
        for (int i = 0; i < layersToRender.size; i++) {
            IntSetIterator iterator = entitiesPerLayer.get(layersToRender.get(i)).iterator();
            while (iterator.hasNext) {
                entityConsumer.accept(iterator.next());
            }
        }
    }
    
    protected class EntityIterator {
        
        private int layerIndex;
        private IntSetIterator layerIterator;
        
        private void reset() {
            layerIndex = 0;
            layerIterator = entitiesPerLayer.get(layersToRender.get(layerIndex)).iterator();
        }
        
        public boolean hasNext() {
            while (!layerIterator.hasNext && layerIndex < layersToRender.size) {
                layerIterator = entitiesPerLayer.get(layersToRender.get(layerIndex)).iterator();
                layerIndex++;
            };
            return layerIterator.hasNext;
        }
        
        public int next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return layerIterator.next();
        }
        
    }
    
}
