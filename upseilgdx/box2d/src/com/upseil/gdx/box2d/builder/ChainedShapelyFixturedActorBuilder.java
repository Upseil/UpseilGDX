package com.upseil.gdx.box2d.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.upseil.gdx.box2d.builder.base.AbstractFixtureBuilderBase;
import com.upseil.gdx.box2d.builder.base.ChainedFixtureBuilderBase;
import com.upseil.gdx.box2d.builder.base.FixturedActorBuilderBase;
import com.upseil.gdx.box2d.builder.shape.ShapeBuilder;
import com.upseil.gdx.pool.PooledPair;
import com.upseil.gdx.scene2d.PolygonActor;

public class ChainedShapelyFixturedActorBuilder extends AbstractFixtureBuilderBase<PooledPair<FixtureDef, Actor>>
                                                implements ChainedFixtureBuilderBase<PooledPair<FixtureDef, Actor>, BodiedActorBuilder>,
                                                           FixturedActorBuilderBase<PooledPair<FixtureDef, Actor>> {
    
    private final BodiedActorBuilder parent;
    private final Pool<PooledPair<FixtureDef, Actor>> pool;
    
    private Drawable image;
    
    private TextureRegion texture;
    private float[] vertices;
    
    private final Color color;

    public ChainedShapelyFixturedActorBuilder(FixtureDef fixtureDefinition, ShapeBuilder<?> shape, BodiedActorBuilder parent, Pool<PooledPair<FixtureDef, Actor>> pool) {
        super(fixtureDefinition, shape);
        this.parent = parent;
        this.pool = pool;
        this.color = new Color(1, 1, 1, 1);
    }
    
    @Override
    public PooledPair<FixtureDef, Actor> build() {
        fixtureDefinition.shape = shape.build();

        Rectangle shapeBounds = shape.getBounds();
        Actor actor = createActor();
        actor.setOrigin(shapeBounds.width / 2, shapeBounds.height / 2);
        actor.setBounds(shapeBounds.x, shapeBounds.y, shapeBounds.width, shapeBounds.height);
        actor.setRotation(shape.getAngle() * MathUtils.radiansToDegrees);
        actor.setColor(color);
        
        return pool.obtain().set(fixtureDefinition, actor);
    }

    private Actor createActor() {
        if (vertices != null) {
            return new PolygonActor(texture, vertices);
        }
        if (image != null) {
            return new Image(image);
        }
        return new Actor();
    }

    @Override
    public BodiedActorBuilder endFixture() {
        return parent.endFixture(this);
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withImage(Drawable image) {
        this.image = image;
        changed = true;
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withImage(String name) {
        return withImage(parent.getSkin().getDrawable(name));
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withTexture(TextureRegion texture) {
        if (vertices == null) {
            this.image = new TextureRegionDrawable(texture);
        } else {
            this.texture = texture;
        }
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withTexture(String name) {
        if (vertices == null) {
            withImage(name);
        } else {
            texture = parent.getSkin().getRegion(name);
        }
        return this;
    }
    
    void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withColor(Color color) {
        this.color.set(color);
        changed = true;
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withColor(String name) {
        return withColor(parent.getSkin().getColor(name));
    }

//- Overriding members for concrete return type -----------------------------------------

    @Override
    public ChainedShapelyFixturedActorBuilder withFriction(float friction) {
        super.withFriction(friction);
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withRestitution(float restitution) {
        super.withRestitution(restitution);
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withDensity(float density) {
        super.withDensity(density);
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder asSensor(boolean isSensor) {
        super.asSensor(isSensor);
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder asSensor() {
        super.asSensor();
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder notAsSensor() {
        super.notAsSensor();
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withCategoryBits(short categoryBits) {
        super.withCategoryBits(categoryBits);
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withMaskBits(short maskBits) {
        super.withMaskBits(maskBits);
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withGroupIndex(short groupIndex) {
        super.withGroupIndex(groupIndex);
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withFilter(Filter filter) {
        super.withFilter(filter);
        return this;
    }

    @Override
    public ChainedShapelyFixturedActorBuilder withFilter(short categoryBits, short maskBits, short groupIndex) {
        super.withFilter(categoryBits, maskBits, groupIndex);
        return this;
    }
    
}
