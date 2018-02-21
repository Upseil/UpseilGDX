package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.upseil.gdx.box2d.builder.base.AbstractShapelessFixtureBuilderBase;
import com.upseil.gdx.box2d.builder.shape.ChainedPolygonShapeBuilder;
import com.upseil.gdx.box2d.builder.shape.ShapeBuilder;
import com.upseil.gdx.box2d.util.Fixtures;
import com.upseil.gdx.pool.pair.PairPool;

public class ChainedFixturedActorBuilder extends AbstractShapelessFixtureBuilderBase<ChainedShapelyFixturedActorBuilder> {
    
    public static final PairPool<FixtureDef, Actor> DefaultPool = new PairPool<>(4, 100);
    
    private final FixtureDef template;
    private final BodiedActorBuilder parent;

    public ChainedFixturedActorBuilder(FixtureDef template, BodiedActorBuilder parent) {
        this.template = template;
        this.parent = parent;
    }

    public ChainedPolygonShapeBuilder<ChainedShapelyFixturedActorBuilder> withPolygonShapeAsActor() {
        ChainedPolygonShapeBuilder<ChainedShapelyFixturedActorBuilder> polygonShapeBuilder = new ChainedPolygonShapeBuilder<ChainedShapelyFixturedActorBuilder>() {
            @Override
            protected ChainedShapelyFixturedActorBuilder createParent() {
                return createShapelyFixtureBuilder(this);
            }
            @Override
            public ChainedShapelyFixturedActorBuilder endShape() {
                this.parent.setVertices(vertices().toArray());
                return this.parent;
            }
        };
        polygonShapeBuilder.normalizeOriginToCenter();
        return polygonShapeBuilder;
    }

    @Override
    protected ChainedShapelyFixturedActorBuilder createShapelyFixtureBuilder(ShapeBuilder<?> shape) {
        return new ChainedShapelyFixturedActorBuilder(Fixtures.copy(template), shape, parent, DefaultPool);
    }
    
}
