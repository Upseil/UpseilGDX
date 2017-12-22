package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.upseil.gdx.box2d.builder.base.AbstractShapelessFixtureBuilderBase;
import com.upseil.gdx.box2d.builder.shape.ShapeBuilder;
import com.upseil.gdx.box2d.util.Fixtures;

public class FixtureBuilder extends AbstractShapelessFixtureBuilderBase<ShapelyFixtureBuilder> {
    
    protected final FixtureDef template;
    
    public FixtureBuilder() {
        this(Fixtures.DefaultFixtureDefinition);
    }

    public FixtureBuilder(FixtureDef template) {
        this.template = Fixtures.copy(template);
    }

    @Override
    protected ShapelyFixtureBuilder createShapelyFixtureBuilder(ShapeBuilder<?> shape) {
        return new ShapelyFixtureBuilder(Fixtures.copy(template), shape);
    }
    
}
