package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.upseil.gdx.box2d.builder.base.AbstractShapelessFixtureBuilderBase;
import com.upseil.gdx.box2d.builder.shape.ShapeBuilder;
import com.upseil.gdx.box2d.util.Fixtures;

public class ChainedFixtureBuilder extends AbstractShapelessFixtureBuilderBase<ChainedShapelyFixtureBuilder> {
    
    private final FixtureDef template;
    private final BodyBuilder parent;

    public ChainedFixtureBuilder(FixtureDef template, BodyBuilder parent) {
        this.template = Fixtures.copy(template);
        this.parent = parent;
    }

    @Override
    protected ChainedShapelyFixtureBuilder createShapelyFixtureBuilder(ShapeBuilder<?> shape) {
        return new ChainedShapelyFixtureBuilder(Fixtures.copy(template), shape, parent);
    }
    
}
