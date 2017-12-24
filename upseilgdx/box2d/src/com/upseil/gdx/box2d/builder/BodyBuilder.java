package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.upseil.gdx.box2d.builder.base.AbstractBodyBuilderBase;
import com.upseil.gdx.box2d.builder.base.BodyBuilderBase;
import com.upseil.gdx.box2d.builder.base.ChainedFixtureBuilderBase;
import com.upseil.gdx.box2d.builder.base.ShapelessFixtureBuilderBase;
import com.upseil.gdx.box2d.util.Fixtures;

public class BodyBuilder extends AbstractBodyBuilderBase<Body, FixtureDef> {
    
    protected final World world;
    protected final Array<ChainedShapelyFixtureBuilder> fixtures;
    
    public BodyBuilder(World world, BodyDef template) {
        super(template);
        this.world = world;
        fixtures = new Array<>();
    }
    
    @Override
    public Body build() {
        Body body = world.createBody(bodyDefinition);
        for (ChainedShapelyFixtureBuilder fixture : fixtures) {
            body.createFixture(fixture.build());
        }
        return body;
    }
    
    @Override
    public ChainedFixtureBuilder beginFixture(FixtureDef template) {
        return new ChainedFixtureBuilder(template, this);
    }
    
    @Override
    public ShapelessFixtureBuilderBase<? extends ChainedFixtureBuilderBase<FixtureDef, ? extends BodyBuilderBase<Body, FixtureDef>>> beginFixture() {
        return beginFixture(Fixtures.DefaultFixtureDefinition);
    }

    BodyBuilder endFixture(ChainedShapelyFixtureBuilder fixture) {
        applyFilter(fixture);
        fixtures.add(fixture);
        bounds.merge(fixture.getBounds());
        changed = true;
        return this;
    }
    
    @Override
    public void dispose() {
        for (ChainedShapelyFixtureBuilder fixture : fixtures) {
            fixture.dispose();
        }
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public BodyBuilder withCategoryBits(short categoryBits) {
        super.withCategoryBits(categoryBits);
        return this;
    }

    @Override
    public BodyBuilder withMaskBits(short maskBits) {
        super.withMaskBits(maskBits);
        return this;
    }

    @Override
    public BodyBuilder withGroupIndex(short groupIndex) {
        super.withGroupIndex(groupIndex);
        return this;
    }

    @Override
    public BodyBuilder withFilter(Filter filter) {
        super.withFilter(filter);
        return this;
    }

    @Override
    public BodyBuilder withFilter(short categoryBits, short maskBits, short groupIndex) {
        super.withFilter(categoryBits, maskBits, groupIndex);
        return this;
    }

    @Override
    public BodyBuilder type(BodyType type) {
        super.type(type);
        return this;
    }

    @Override
    public BodyBuilder withStaticBody() {
        super.withStaticBody();
        return this;
    }

    @Override
    public BodyBuilder withKinematicBody() {
        super.withKinematicBody();
        return this;
    }

    @Override
    public BodyBuilder withDynamicBody() {
        super.withDynamicBody();
        return this;
    }

    @Override
    public BodyBuilder at(float x, float y) {
        super.at(x, y);
        return this;
    }

    @Override
    public BodyBuilder at(Vector2 position) {
        super.at(position);
        return this;
    }

    @Override
    public BodyBuilder rotatedByRadians(float angle) {
        super.rotatedByRadians(angle);
        return this;
    }

    @Override
    public BodyBuilder rotatedByDegrees(float angle) {
        super.rotatedByDegrees(angle);
        return this;
    }

    @Override
    public BodyBuilder withLinearVelocity(float x, float y) {
        super.withLinearVelocity(x, y);
        return this;
    }

    @Override
    public BodyBuilder withLinearVelocity(Vector2 velocity) {
        super.withLinearVelocity(velocity);
        return this;
    }

    @Override
    public BodyBuilder withAngularVelocityInRadians(float velocity) {
        super.withAngularVelocityInRadians(velocity);
        return this;
    }

    @Override
    public BodyBuilder withAngularVelocityInDegrees(float velocity) {
        super.withAngularVelocityInDegrees(velocity);
        return this;
    }

    @Override
    public BodyBuilder withLinearDamping(float dampingFactor) {
        super.withLinearDamping(dampingFactor);
        return this;
    }

    @Override
    public BodyBuilder withAngularDamping(float dampingFactor) {
        super.withAngularDamping(dampingFactor);
        return this;
    }

    @Override
    public BodyBuilder allowingSleep(boolean allowSleep) {
        super.allowingSleep(allowSleep);
        return this;
    }

    @Override
    public BodyBuilder allowingSleep() {
        super.allowingSleep();
        return this;
    }

    @Override
    public BodyBuilder prohibtingSleep() {
        super.prohibtingSleep();
        return this;
    }

    @Override
    public BodyBuilder isAwake(boolean isAwake) {
        super.isAwake(isAwake);
        return this;
    }

    @Override
    public BodyBuilder awake() {
        super.awake();
        return this;
    }

    @Override
    public BodyBuilder sleeping() {
        super.sleeping();
        return this;
    }

    @Override
    public BodyBuilder withFixedRotation(boolean fixedRotation) {
        super.withFixedRotation(fixedRotation);
        return this;
    }

    @Override
    public BodyBuilder withFixedRotation() {
        super.withFixedRotation();
        return this;
    }

    @Override
    public BodyBuilder withoutFixedRotation() {
        super.withoutFixedRotation();
        return this;
    }

    @Override
    public BodyBuilder asBullet(boolean isBullet) {
        super.asBullet(isBullet);
        return this;
    }

    @Override
    public BodyBuilder asBullet() {
        super.asBullet();
        return this;
    }

    @Override
    public BodyBuilder notAsBullet() {
        super.notAsBullet();
        return this;
    }

    @Override
    public BodyBuilder active(boolean isActive) {
        super.active(isActive);
        return this;
    }

    @Override
    public BodyBuilder active() {
        super.active();
        return this;
    }

    @Override
    public BodyBuilder inactive() {
        super.inactive();
        return this;
    }

    @Override
    public BodyBuilder withGravityScale(float gravityScale) {
        super.withGravityScale(gravityScale);
        return this;
    }

    @Override
    public BodyBuilder withZeroGravity() {
        super.withZeroGravity();
        return this;
    }
    
}
