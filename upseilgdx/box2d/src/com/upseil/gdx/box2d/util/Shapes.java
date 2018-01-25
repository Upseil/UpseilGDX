package com.upseil.gdx.box2d.util;

import com.upseil.gdx.box2d.builder.shape.BoxShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ChainShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.CircleShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.PolygonShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.SimpleBoxShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.SimpleChainShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.SimpleCircleShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.SimplePolygonShapeBuilder;

public final class Shapes {
    
    public static CircleShapeBuilder newCircleShape() {
        return new SimpleCircleShapeBuilder();
    }
    
    public static BoxShapeBuilder newBoxShape() {
        return new SimpleBoxShapeBuilder();
    }
    
    public static PolygonShapeBuilder newPolygonShape() {
        return new SimplePolygonShapeBuilder();
    }
    
    public static ChainShapeBuilder newChainShape() {
        return new SimpleChainShapeBuilder();
    }
    
//    public static EdgeShapeBuilder newEdgeShape() {
//        return new SimpleEdgeShapeBuilder();
//    }
    
}
