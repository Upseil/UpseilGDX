package com.upseil.gdx.box2d.builder;

import static com.upseil.gdx.box2d.util.Bodies.DefaultBodyDefinition;
import static com.upseil.gdx.box2d.util.Bodies.copy;
import static com.upseil.gdx.box2d.util.Fixtures.DefaultFixtureDefinition;
import static com.upseil.gdx.box2d.util.Fixtures.copy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import com.upseil.gdx.util.Pair;
import com.upseil.gdx.util.builder.Builder;
import com.upseil.gdx.util.exception.NotImplementedException;

// FIXME Increase memory efficiency
public class BodiedActorBuilder extends AbstractBodyBuilderBase<BodiedActorBuilder> implements Builder<Pair<Body, Actor>>, Disposable {
    
    private final World world;
    private final Array<InternalFixtureBuilder> fixtures;
    
    public BodiedActorBuilder(World world) {
        super(copy(DefaultBodyDefinition));
        setBuilder(this);
        
        this.world = world;
        fixtures = new Array<>(2);
    }
    
    public ShapelessFixtureBuilder beginFixture() {
        InternalFixtureBuilder fixture = new InternalFixtureBuilder();
        fixtures.add(fixture);
        return fixture;
    }

    @Override
    public Pair<Body, Actor> build() {
        Body body = world.createBody(bodyDefinition);
        for (InternalFixtureBuilder fixtureBuilder : fixtures) {
            body.createFixture(fixtureBuilder.getFixtureDefinition());
        }
        
        Actor actor;
        Rectangle actorBounds = new Rectangle();
        if (fixtures.size > 1) {
            Group group = new Group();
            group.setTransform(true); // FIXME Do this only if necessary
            for (InternalFixtureBuilder fixtureBuilder : fixtures) {
                Actor fixtureActor = fixtureBuilder.getActor();
                actorBounds.merge(fixtureActor.getX(), fixtureActor.getY());
                actorBounds.merge(fixtureActor.getX() + fixtureActor.getWidth(), fixtureActor.getY() + fixtureActor.getHeight());
                group.addActor(fixtureActor);
            }
            for (Actor child : group.getChildren()) {
                child.setPosition(child.getX() + actorBounds.width / 2, child.getY() + actorBounds.height / 2);
            }
            actor = group;
        } else {
            InternalFixtureBuilder fixtureBuilder = fixtures.get(0);
            actor = fixtureBuilder.getActor();
            actorBounds.setSize(fixtureBuilder.width, fixtureBuilder.height);
        }
        actor.setOrigin(actorBounds.width / 2, actorBounds.height / 2);
        actor.setSize(actorBounds.width, actorBounds.height);
        actor.setPosition(body.getPosition().x - (actorBounds.width / 2), body.getPosition().y - (actorBounds.height / 2));
        actor.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        
        return new Pair<>(body, actor);
    }
    
    @Override
    public void dispose() {
        for (InternalFixtureBuilder fixtureBuilder : fixtures) {
            fixtureBuilder.dispose();
        }
    }
    
    public interface FixtureBuilder extends FixtureBuilderBase<FixtureBuilder>, Builder<Pair<Body, Actor>> {
        
        FixtureBuilder image(Drawable image);
        FixtureBuilder color(Color color);
        
        BodiedActorBuilder endFixture();
        
    }
    
    public interface ShapelessFixtureBuilder {

        FixtureBuilder withSimpleBoxShape(float width, float height);
        FixtureBuilder withCircleShape(float radius);
        FixtureBuilder withCircleShape(float radius, Vector2 position);
        FixtureBuilder withCircleShape(float radius, float x, float y);
        
        BoxShapeBuilder withBoxShape(float width, float height);
        PolygonShapeBuilder withPolygonShape();
        
    }
    
    public interface ShapeBuilder {

        FixtureBuilder endShape();
        FixtureBuilder endShape(float radius);
        
    }
    
    public interface BoxShapeBuilder extends ShapeBuilder {
        
        BoxShapeBuilder position(float x, float y);
        
        BoxShapeBuilder angleInRadians(float angle);
        BoxShapeBuilder angleInDegrees(float angle);
        
    }
    
    public interface PolygonShapeBuilder extends ShapeBuilder {

        PolygonShapeBuilder addVertix(Vector2 vertix);
        PolygonShapeBuilder addVertix(float x, float y);

        PolygonShapeBuilder vertices(Vector2[] vertices);
        PolygonShapeBuilder vertices(float[] vertices);
        
    }
    
    private class InternalFixtureBuilder extends AbstractFixtureBuilderBase<FixtureBuilder>
                                         implements FixtureBuilder, ShapelessFixtureBuilder, BoxShapeBuilder, PolygonShapeBuilder, Disposable {
        
        private Drawable image;
        private final Color color;
        
        private Shape.Type shapeType;
        private float radius;
        private Vector2 position;
        private float angle;
        private FloatArray vertices;
        
        private float width;
        private float height;
        
        private Shape shape;

        public InternalFixtureBuilder() {
            super(copy(DefaultFixtureDefinition));
            super.setBuilder(this);
            
            color = new Color(1, 1, 1, 1);
            
            position = new Vector2();
            vertices = new FloatArray(16);
        }
        
        //------------- InternalFixtureBuilder ----------------------------------------------------------------------
        
        private FixtureDef getFixtureDefinition() {
            if (fixtureDefinition.shape == null) {
                buildShape();
            }
            return fixtureDefinition;
        }
        
        private Actor getActor() {
            Actor actor = image != null ? new Image(image) : new Actor();
            actor.setOrigin(width / 2, height / 2);
            actor.setSize(width, height);
            actor.setColor(color);
            actor.setPosition(position.x - (width / 2), position.y - (height / 2));
            actor.setRotation(angle * MathUtils.radiansToDegrees);
            return actor;
        }
        
        private void buildShape() {
            Shape shape;
            switch (shapeType) {
            case Circle:
                CircleShape circle = new CircleShape();
                circle.setRadius(radius);
                circle.setPosition(position);
                shape = circle;
                break;
            case Polygon:
                if (vertices.size > 0) shape = buildPolygonShape();
                else                   shape = buildBoxShape();
                break;
            case Edge:
            case Chain:
            default:
                throw new NotImplementedException("Shape creation not implemented for shapes of type " + shapeType);
            }
            fixtureDefinition.shape = shape;
        }

        private Shape buildPolygonShape() {
            PolygonShape shape = new PolygonShape();
            shape.set(vertices.items);
            
            float minX = Float.MAX_VALUE;
            float maxX = -Float.MAX_VALUE;
            float minY = Float.MAX_VALUE;
            float maxY = -Float.MAX_VALUE;
            float[] data = vertices.items;
            for (int index = 0; index < vertices.size; index += 2) {
                float x = data[index];
                float y = data[index + 1];

                minX = Math.min(minX, x);
                maxX = Math.max(maxX, x);
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }
            width = maxX - minX;
            height = maxY - minY;
            
            return shape;
        }

        private Shape buildBoxShape() {
            PolygonShape shape = new PolygonShape();
            if (angle == 0 && position.x == 0 && position.y == 0) {
                shape.setAsBox(width / 2, height / 2);
            } else {
                shape.setAsBox(width / 2, height / 2, position, angle);
            }
            return shape;
        }
        
        //------------- ShapelessFixtureBuilder --------------------------------------------------------------------- 

        @Override
        public BoxShapeBuilder withBoxShape(float width, float height) {
            withSimpleBoxShape(width, height);
            return this;
        }
        
        @Override
        public FixtureBuilder withSimpleBoxShape(float width, float height) {
            shapeType = Shape.Type.Polygon;
            this.width = width;
            this.height = height;
            return this;
        }
        
        @Override
        public FixtureBuilder withCircleShape(float radius, Vector2 position) {
            return withCircleShape(radius, position.x, position.y);
        }
        
        @Override
        public FixtureBuilder withCircleShape(float radius, float x, float y) {
            position.x = x;
            position.y = y;
            return withCircleShape(radius);
        }
        
        @Override
        public FixtureBuilder withCircleShape(float radius) {
            shapeType = Shape.Type.Circle;
            this.radius = radius;
            width = radius * 2;
            height = radius * 2;
            return null;
        }
        
        @Override
        public PolygonShapeBuilder withPolygonShape() {
            shapeType = Shape.Type.Polygon;
            return this;
        }
        
        //------------- BoxShapeBuilder -----------------------------------------------------------------------------
        
        public BoxShapeBuilder position(float x, float y) {
            position.x = x;
            position.y = y;
            return this;
        }
        
        public BoxShapeBuilder angleInRadians(float angle) {
            this.angle = angle;
            return this;
        }
        
        public BoxShapeBuilder angleInDegrees(float angle) {
            this.angle = angle * MathUtils.degreesToRadians;
            return this;
        }
        
        //------------- PolygonShapeBuilder -------------------------------------------------------------------------

        public PolygonShapeBuilder addVertix(Vector2 vertix) {
            return addVertix(vertix.x, vertix.y);
        }
        
        public PolygonShapeBuilder addVertix(float x, float y) {
            vertices.add(x);
            vertices.add(y);
            return this;
        }

        public PolygonShapeBuilder vertices(Vector2[] vertices) {
            for (Vector2 vertix : vertices) {
                addVertix(vertix.x, vertix.y);
            }
            return this;
        }
        
        public PolygonShapeBuilder vertices(float[] vertices) {
            for (float value : vertices) {
                this.vertices.add(value);
            }
            return this;
        }
        
        //------------- FixtureBuilder ------------------------------------------------------------------------------
        
        public FixtureBuilder image(Drawable image) {
            this.image = image;
            return this;
        }
        
        public FixtureBuilder color(Color color) {
            this.color.set(color);
            return this;
        }
        
        //------------- Control Flow --------------------------------------------------------------------------------
        
        @Override
        public FixtureBuilder endShape() {
            return this;
        }
        
        @Override
        public FixtureBuilder endShape(float radius) {
            this.radius = radius;
            return this;
        }
        
        @Override
        public BodiedActorBuilder endFixture() {
            return BodiedActorBuilder.this;
        }

        @Override
        public Pair<Body, Actor> build() {
            return BodiedActorBuilder.this.build();
        }
        
        @Override
        public void dispose() {
            if (shape != null) {
                shape.dispose();
            }
        }
        
    }
    
}
