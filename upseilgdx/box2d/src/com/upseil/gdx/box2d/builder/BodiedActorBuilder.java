package com.upseil.gdx.box2d.builder;

import static com.upseil.gdx.box2d.util.Bodies.DefaultBodyDefinition;
import static com.upseil.gdx.box2d.util.Bodies.copy;
import static com.upseil.gdx.box2d.util.Fixtures.DefaultFixtureDefinition;
import static com.upseil.gdx.box2d.util.Fixtures.copy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.upseil.gdx.util.Pair;
import com.upseil.gdx.util.builder.Builder;

// FIXME Increase memory efficiency
public class BodiedActorBuilder extends AbstractBodyBuilderBase<BodiedActorBuilder> implements Builder<Pair<Body, Actor>>, Disposable {

    private final World world;
    private final Skin skin;
    private final Array<InternalFixtureBuilder> fixtures;
    private final Rectangle bounds;
    
    public BodiedActorBuilder(World world) {
        this(world, null, DefaultBodyDefinition);
    }
    
    public BodiedActorBuilder(World world, Skin skin) {
        this(world, skin, DefaultBodyDefinition);
    }
    
    public BodiedActorBuilder(World world, Skin skin, BodyDef template) {
        super(copy(template));
        super.builder = this;
        
        this.world = world;
        this.skin = skin;
        fixtures = new Array<>(2);
        bounds = new Rectangle();
    }
    
    public ShapelessFixtureBuilder beginFixture() {
        InternalFixtureBuilder fixture = new InternalFixtureBuilder();
        if (hasCategoryBits) {
            fixture.withCategoryBits(categoryBits);
        }
        if (hasMaskBits) {
            fixture.withMaskBits(maskBits);
        }
        if (hasGroupIndex) {
            fixture.withGroupIndex(groupIndex);
        }
        fixtures.add(fixture);
        return fixture;
    }
    
    private BodiedActorBuilder fixtureFinished(InternalFixtureBuilder fixtureBuilder) {
        bounds.merge(fixtureBuilder.getBounds());
        return this;
    }

    @Override
    public Pair<Body, Actor> build() {
        Body body = world.createBody(bodyDefinition);
        for (InternalFixtureBuilder fixtureBuilder : fixtures) {
            body.createFixture(fixtureBuilder.getFixtureDefinition(bounds));
        }
        
        Actor actor;
        if (fixtures.size > 1) {
            Group group = new Group();
            group.setTransform(true); // FIXME Do this only if necessary
            for (InternalFixtureBuilder fixtureBuilder : fixtures) {
                Actor fixtureActor = fixtureBuilder.createActor();
                fixtureActor.setPosition(fixtureActor.getX() + bounds.width / 2, fixtureActor.getY() + bounds.height / 2);
                group.addActor(fixtureActor);
            }
            actor = group;
        } else {
            InternalFixtureBuilder fixtureBuilder = fixtures.get(0);
            actor = fixtureBuilder.createActor();
        }
        actor.setOrigin(bounds.width / 2, bounds.height / 2);
        actor.setSize(bounds.width, bounds.height);
        actor.setPosition(body.getPosition().x - (bounds.width / 2), body.getPosition().y - (bounds.height / 2));
        actor.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        
        return new Pair<>(body, actor);
    }
    
    @Override
    public void dispose() {
        for (InternalFixtureBuilder fixtureBuilder : fixtures) {
            fixtureBuilder.dispose();
        }
    }
    
    private class InternalFixtureBuilder extends AbstractFixtureBuilderBase<FixtureBuilder>
                                         implements FixtureBuilder, ShapelessFixtureBuilder, Disposable {//, BoxShapeBuilder, PolygonShapeBuilder, Disposable {
        
        private Drawable image;
        private final Color color;
        
        private ShapeBuilderBase<?> shapeBuilder;

        public InternalFixtureBuilder() {
            super(copy(DefaultFixtureDefinition));
            super.builder = this;
            
            color = new Color(1, 1, 1, 1);
        }
        
        private FixtureDef getFixtureDefinition(Rectangle bodyBounds) {
            if (fixtureDefinition.shape == null) {
                fixtureDefinition.shape = shapeBuilder.getShape();
            }
            return fixtureDefinition;
        }
        
        private Actor createActor() {
            Rectangle shapeBounds = shapeBuilder.getBounds();
            Actor actor = image != null ? new Image(image) : new Actor();
            actor.setOrigin(shapeBounds.width / 2, shapeBounds.height / 2);
            actor.setSize(shapeBounds.width, shapeBounds.height);
            actor.setColor(color);
            actor.setPosition(shapeBounds.x, shapeBounds.y);
            actor.setRotation(shapeBuilder.getAngle() * MathUtils.degreesToRadians);
            return actor;
        }
        
        private Rectangle getBounds() {
            return shapeBuilder.getBounds();
        }
        
        @Override
        public BoxShapeBuilder withBoxShape(float width, float height) {
            if (shapeBuilder != null) {
                throw new IllegalStateException("The shape of this fixture already has been defined");
            }
            
            InternalBoxShapeBuilder boxShapeBuilder = new InternalBoxShapeBuilder(this, width, height);
            shapeBuilder = boxShapeBuilder;
            return boxShapeBuilder;
        }
        
        @Override
        public CircleShapeBuilder withCircleShape(float radius) {
            if (shapeBuilder != null) {
                throw new IllegalStateException("The shape of this fixture already has been defined");
            }
            
            CircleShapeBuilder circleShapeBuilder = new InternalCircleShapeBuilder(this, radius);
            shapeBuilder = circleShapeBuilder;
            return circleShapeBuilder;
        }
        
        @Override
        public PolygonShapeBuilder withPolygonShape() {
            if (shapeBuilder != null) {
                throw new IllegalStateException("The shape of this fixture already has been defined");
            }
            
            InternalPolygonShapeBuilder polygonShapeBuilder = new InternalPolygonShapeBuilder(this);
            shapeBuilder = polygonShapeBuilder;
            return polygonShapeBuilder;
        }
        
        public FixtureBuilder withImage(Drawable image) {
            this.image = image;
            return this;
        }
        
        @Override
        public FixtureBuilder withImage(String name) {
            return withImage(skin.getDrawable(name));
        }
        
        public FixtureBuilder withColor(Color color) {
            this.color.set(color);
            return this;
        }
        
        @Override
        public FixtureBuilder withColor(String name) {
            return withColor(skin.getColor(name));
        }
        
        @Override
        public BodiedActorBuilder endFixture() {
            return fixtureFinished(this);
        }
        
        @Override
        public void dispose() {
            if (shapeBuilder != null) {
                shapeBuilder.dispose();
            }
        }
        
    }
    
}
