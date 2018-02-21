package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.upseil.gdx.box2d.builder.base.FixtureBuilderBase;

public interface FixtureDefinitionBuilder extends FixtureBuilderBase<FixtureDef> {
    
//- Overriding members for concrete return type -----------------------------------------
    
    FixtureDefinitionBuilder withFriction(float friction);
    FixtureDefinitionBuilder withRestitution(float restitution);
    FixtureDefinitionBuilder withDensity(float density);
    
    FixtureDefinitionBuilder asSensor(boolean isSensor);
    default FixtureDefinitionBuilder asSensor() {
        FixtureBuilderBase.super.asSensor();
        return this;
    }
    default FixtureDefinitionBuilder notAsSensor() {
        FixtureBuilderBase.super.notAsSensor();
        return this;
    }
    
    FixtureDefinitionBuilder withCategoryBits(short categoryBits);
    FixtureDefinitionBuilder withMaskBits(short maskBits);
    FixtureDefinitionBuilder withGroupIndex(short groupIndex);
    
    default FixtureDefinitionBuilder withFilter(Filter filter) {
        FixtureBuilderBase.super.withFilter(filter);
        return this;
    }
    
    default FixtureDefinitionBuilder withFilter(short categoryBits, short maskBits, short groupIndex) {
        FixtureBuilderBase.super.withFilter(categoryBits, maskBits, groupIndex);
        return this;
    }
    
}
