package com.upseil.gdx.box2d.util;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.upseil.gdx.box2d.builder.FixtureBuilder;

public final class Fixtures {
    
    public static final FixtureDef DefaultFixtureDefinition = new FixtureDef();
    
    public static FixtureBuilder newFixtureDefinition() {
        return newFixtureDefinition(DefaultFixtureDefinition);
    }
    
    public static FixtureBuilder newFixtureDefinition(FixtureDef template) {
        return new FixtureBuilder(copy(template));
    }

    public static FixtureDef copy(FixtureDef fixtureDefinition) {
        FixtureDef clone = new FixtureDef();
        apply(fixtureDefinition, clone);
        return clone;
    }
    
    private static void apply(FixtureDef source, FixtureDef target) {
        target.shape = source.shape;
        target.friction = source.friction;
        target.restitution = source.restitution;
        target.density = source.density;
        target.isSensor = source.isSensor;
        apply(source.filter, target.filter);
    }
    
    public static Filter copy(Filter filter) {
        Filter clone = new Filter();
        apply(filter, clone);
        return clone;
    }

    private static void apply(Filter source, Filter target) {
        target.categoryBits = source.categoryBits;
        target.maskBits = source.maskBits;
        target.groupIndex = source.groupIndex;
    }

    private Fixtures() { }
    
}
