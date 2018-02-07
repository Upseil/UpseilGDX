package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.upseil.gdx.box2d.builder.base.AbstractBodyBuilderBase;
import com.upseil.gdx.box2d.util.Fixtures;
import com.upseil.gdx.pool.pair.PooledPair;

public class BodiedActorBuilder extends AbstractBodyBuilderBase<PooledPair<Body, Actor>, PooledPair<FixtureDef, Actor>> {
    
    protected final World world;
    protected final Skin skin;
    protected final Pool<PooledPair<Body, Actor>> pool;
    
    protected final Array<ChainedShapelyFixturedActorBuilder> fixtures;

    public BodiedActorBuilder(World world, Skin skin, Pool<PooledPair<Body, Actor>> pool, BodyDef template) {
        super(template);
        this.world = world;
        this.skin = skin;
        this.pool = pool;
        fixtures = new Array<>();
    }
    
    @Override
    public PooledPair<Body, Actor> build() {
        Body body = world.createBody(bodyDefinition);
        
        Actor actor;
        float halfWidth = bounds.width / 2;
        float halfHeight = bounds.height / 2;
        
        if (fixtures.size > 1) {
            Group group = new Group();
            boolean transform = body.getType() == BodyType.DynamicBody;
            for (ChainedShapelyFixturedActorBuilder fixture : fixtures) {
                PooledPair<FixtureDef, Actor> fixturedActor = fixture.build();
                body.createFixture(fixturedActor.getA());
                
                Actor fixtureActor = fixturedActor.getB();
                fixtureActor.setPosition(fixtureActor.getX() + halfWidth, fixtureActor.getY() + halfHeight);
                group.addActor(fixtureActor);

                transform |= !MathUtils.isEqual(fixtureActor.getRotation(), 0);
                transform |= !MathUtils.isEqual(fixtureActor.getScaleX(), 1);
                transform |= !MathUtils.isEqual(fixtureActor.getScaleY(), 1);
                
                fixturedActor.free();
            }
            group.setTransform(transform);
            actor = group;
        } else {
            ChainedShapelyFixturedActorBuilder fixture = fixtures.get(0);
            PooledPair<FixtureDef, Actor> fixturedActor = fixture.build();
            body.createFixture(fixturedActor.getA());
            actor = fixturedActor.getB();
            fixturedActor.free();
        }

        actor.setOrigin(halfWidth, halfHeight);
        actor.setSize(bounds.width, bounds.height);
        actor.setPosition(body.getPosition().x - halfWidth, body.getPosition().y - halfHeight);
        actor.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        
        return pool.obtain().set(body, actor);
    }
    
    @Override
    public ChainedFixturedActorBuilder beginFixture(FixtureDef template) {
        return new ChainedFixturedActorBuilder(template, this);
    }

    @Override
    public ChainedFixturedActorBuilder beginFixture() {
        return beginFixture(Fixtures.DefaultFixtureDefinition); 
    }

    BodiedActorBuilder endFixture(ChainedShapelyFixturedActorBuilder fixture) {
        applyFilter(fixture);
        fixtures.add(fixture);
        bounds.merge(fixture.getBounds());
        changed = true;
        return this;
    }
    
    @Override
    public void dispose() {
        for (Disposable fixture : fixtures) {
            fixture.dispose();
        }
    }
    
//- Utility methods ---------------------------------------------------------------------
    
    Skin getSkin() {
        return skin;
    }
    
//- Overriding members for concrete return type -----------------------------------------

    @Override
    public BodiedActorBuilder withCategoryBits(short categoryBits) {
        super.withCategoryBits(categoryBits);
        return this;
    }

    @Override
    public BodiedActorBuilder withMaskBits(short maskBits) {
        super.withMaskBits(maskBits);
        return this;
    }

    @Override
    public BodiedActorBuilder withGroupIndex(short groupIndex) {
        super.withGroupIndex(groupIndex);
        return this;
    }

    @Override
    public BodiedActorBuilder withFilter(Filter filter) {
        super.withFilter(filter);
        return this;
    }

    @Override
    public BodiedActorBuilder withFilter(short categoryBits, short maskBits, short groupIndex) {
        super.withFilter(categoryBits, maskBits, groupIndex);
        return this;
    }

    @Override
    public BodiedActorBuilder type(BodyType type) {
        super.type(type);
        return this;
    }

    @Override
    public BodiedActorBuilder withStaticBody() {
        super.withStaticBody();
        return this;
    }

    @Override
    public BodiedActorBuilder withKinematicBody() {
        super.withKinematicBody();
        return this;
    }

    @Override
    public BodiedActorBuilder withDynamicBody() {
        super.withDynamicBody();
        return this;
    }

    @Override
    public BodiedActorBuilder at(float x, float y) {
        super.at(x, y);
        return this;
    }

    @Override
    public BodiedActorBuilder at(Vector2 position) {
        super.at(position);
        return this;
    }

    @Override
    public BodiedActorBuilder rotatedByRadians(float angle) {
        super.rotatedByRadians(angle);
        return this;
    }

    @Override
    public BodiedActorBuilder rotatedByDegrees(float angle) {
        super.rotatedByDegrees(angle);
        return this;
    }

    @Override
    public BodiedActorBuilder withLinearVelocity(float x, float y) {
        super.withLinearVelocity(x, y);
        return this;
    }

    @Override
    public BodiedActorBuilder withLinearVelocity(Vector2 velocity) {
        super.withLinearVelocity(velocity);
        return this;
    }

    @Override
    public BodiedActorBuilder withAngularVelocityInRadians(float velocity) {
        super.withAngularVelocityInRadians(velocity);
        return this;
    }

    @Override
    public BodiedActorBuilder withAngularVelocityInDegrees(float velocity) {
        super.withAngularVelocityInDegrees(velocity);
        return this;
    }

    @Override
    public BodiedActorBuilder withLinearDamping(float dampingFactor) {
        super.withLinearDamping(dampingFactor);
        return this;
    }

    @Override
    public BodiedActorBuilder withAngularDamping(float dampingFactor) {
        super.withAngularDamping(dampingFactor);
        return this;
    }

    @Override
    public BodiedActorBuilder allowingSleep(boolean allowSleep) {
        super.allowingSleep(allowSleep);
        return this;
    }

    @Override
    public BodiedActorBuilder allowingSleep() {
        super.allowingSleep();
        return this;
    }

    @Override
    public BodiedActorBuilder prohibtingSleep() {
        super.prohibtingSleep();
        return this;
    }

    @Override
    public BodiedActorBuilder isAwake(boolean isAwake) {
        super.isAwake(isAwake);
        return this;
    }

    @Override
    public BodiedActorBuilder awake() {
        super.awake();
        return this;
    }

    @Override
    public BodiedActorBuilder sleeping() {
        super.sleeping();
        return this;
    }

    @Override
    public BodiedActorBuilder withFixedRotation(boolean fixedRotation) {
        super.withFixedRotation(fixedRotation);
        return this;
    }

    @Override
    public BodiedActorBuilder withFixedRotation() {
        super.withFixedRotation();
        return this;
    }

    @Override
    public BodiedActorBuilder withoutFixedRotation() {
        super.withoutFixedRotation();
        return this;
    }

    @Override
    public BodiedActorBuilder asBullet(boolean isBullet) {
        super.asBullet(isBullet);
        return this;
    }

    @Override
    public BodiedActorBuilder asBullet() {
        super.asBullet();
        return this;
    }

    @Override
    public BodiedActorBuilder notAsBullet() {
        super.notAsBullet();
        return this;
    }

    @Override
    public BodiedActorBuilder active(boolean isActive) {
        super.active(isActive);
        return this;
    }

    @Override
    public BodiedActorBuilder active() {
        super.active();
        return this;
    }

    @Override
    public BodiedActorBuilder inactive() {
        super.inactive();
        return this;
    }

    @Override
    public BodiedActorBuilder withGravityScale(float gravityScale) {
        super.withGravityScale(gravityScale);
        return this;
    }

    @Override
    public BodiedActorBuilder withZeroGravity() {
        super.withZeroGravity();
        return this;
    }
    
}
