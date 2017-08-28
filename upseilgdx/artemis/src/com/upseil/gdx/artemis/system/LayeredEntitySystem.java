package com.upseil.gdx.artemis.system;

import java.util.NoSuchElementException;

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
        iterator.reset(false);
        return iterator;
    }
    
    protected EntityIterator reverseIterator() {
        iterator.reset(true);
        return iterator;
    }
    
    protected class EntityIterator {
        
        private int layerIndex;
        private boolean reverse;
        private IntSetIterator layerIterator;
        
        private void reset(boolean reverse) {
            this.reverse = reverse;
            layerIndex = reverse ? layersToRender.size - 1 : 0;
            layerIterator = layerIndex < 0 ? null : getLayerIterator();
        }
        
        public boolean hasNext() {
            if (layerIterator == null) {
                return false;
            }
            
            while (!layerIterator.hasNext && hasNextLayer()) {
                layerIterator = getLayerIterator();
                layerIndex += reverse ? -1 : 1;
            };
            return layerIterator.hasNext;
        }

        private IntSetIterator getLayerIterator() {
            return entitiesPerLayer.get(layersToRender.get(layerIndex)).iterator();
        }

        private boolean hasNextLayer() {
            return reverse ? layerIndex >= 0 : layerIndex < layersToRender.size;
        }
        
        public int next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return layerIterator.next();
        }
        
    }
    
}
