package com.upseil.gdx.box2d.builder.shape;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.FloatArray;
import com.upseil.gdx.util.GDXCollections;

public abstract class AbstractVertixBasedShapeBuilder<T extends Shape> extends AbstractShapeBuilder<T> {

    protected final FloatArray firstVertices;
    protected final FloatArray lastVertices;
    protected final FloatArray vertices;
    
    public AbstractVertixBasedShapeBuilder(int firstVerticesCapacity, int lastVerticesCapacity) {
        lastVertices = new FloatArray(lastVerticesCapacity * 2);
        if (firstVerticesCapacity >= 0) {
            firstVertices = new FloatArray(firstVerticesCapacity * 2);
            vertices = new FloatArray((firstVerticesCapacity + lastVerticesCapacity) * 2);
        } else {
            firstVertices = null;
            vertices = null;
        }
    }
    
    protected void internalSet(int index, float x, float y) {
        int componentIndex = index * 2;
        if (MathUtils.isEqual(lastVertices.get(componentIndex), x) &&
            MathUtils.isEqual(lastVertices.get(componentIndex + 1), y)) return;
        
        lastVertices.set(componentIndex, x);
        lastVertices.set(componentIndex + 1, y);
        changed = true;
    }
    
    protected void internalAddFirst(float x, float y) {
        firstVertices.add(y);
        firstVertices.add(x);
        bounds.merge(x, y);
        changed = true;
    }
    
    protected void internalAddLast(float x, float y) {
        lastVertices.add(x);
        lastVertices.add(y);
        bounds.merge(x, y);
        changed = true;
    }
    
    protected FloatArray vertices() {
        if (vertices == null) return lastVertices;
        if (!changed) return vertices;
        
        vertices.ensureCapacity(Math.max(0, (firstVertices.size + lastVertices.size) - vertices.size));
        vertices.clear();
        GDXCollections.revertedForEach(firstVertices, vertices::add);
        GDXCollections.forEach(lastVertices, vertices::add);
        return vertices;
    }
    
}