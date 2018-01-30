package com.upseil.gdx.box2d.testbed;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.upseil.gdx.artemis.component.ActorComponent;
import com.upseil.gdx.artemis.component.InputHandler;
import com.upseil.gdx.artemis.component.Layer;
import com.upseil.gdx.artemis.component.Scene;
import com.upseil.gdx.artemis.system.ClearScreenSystem;
import com.upseil.gdx.artemis.system.LayeredInputSystem;
import com.upseil.gdx.artemis.system.LayeredSceneRenderSystem;
import com.upseil.gdx.box2d.builder.BodiedActorBuilder;
import com.upseil.gdx.box2d.builder.BodyBuilder;
import com.upseil.gdx.box2d.component.BodyComponent;
import com.upseil.gdx.box2d.component.Box2DWorld;
import com.upseil.gdx.box2d.system.Box2DDebugRenderer;
import com.upseil.gdx.box2d.system.PhysicsSystem;
import com.upseil.gdx.box2d.util.Bodies;
import com.upseil.gdx.pool.PooledPair;
import com.upseil.gdx.scene2d.PolygonActor;
import com.upseil.gdx.scene2d.util.TextureDrawable;
import com.upseil.gdx.testbed.ArtemisTestbedApplication;

public class BodiedActorTestbed extends ArtemisTestbedApplication {
    
    public static void main(String[] args) {
        configuration.width = 1920;
        configuration.height = 1080;
        launch(new BodiedActorTestbed());
    }

    private LayeredSceneRenderSystem<PolygonSpriteBatch> renderSystem;
    private Box2DDebugRenderer debugRenderer;

    private ComponentMapper<Scene> sceneMapper;
    
    private final float worldWidth = 160;
    private final float worldHeight = 90;
    private final float borderSize = 2;
    
    private Entity worldEntity;
    private com.badlogic.gdx.physics.box2d.World physicsWorld;
    
    private final float angularVelocity = 360 / 5.0f;

    @Override
    protected World createWorld() {
        WorldConfiguration worldConfiguration = new WorldConfigurationBuilder()
                .with(new LayeredInputSystem())
                .with(new ClearScreenSystem())
                .with(new LayeredSceneRenderSystem<>(new PolygonSpriteBatch()))
                .with(new Box2DDebugRenderer())
                .with(new PhysicsSystem(1.0f / 300, 6, 2))
                .build();
        return new World(worldConfiguration);
    }

    @Override
    protected void initialize() {
        initializePhysicsAndStage();

        float[] vertices = { -3, 0,  0, 3,  3, 0,  0, -6 };
        createRotatedPolygons(vertices, 10, 10, 20);
        
        vertices = new float[] { -3, -3,  0, 3,  3, -3 };
        createRotatedPolygons(vertices, 10, 10, 40);
        
        vertices = new float[] { -3 - 1.5f, 0 - 1.5f,  0 - 1.5f, 3 - 1.5f,  3 - 1.5f, 4.5f - 1.5f,  7.5f - 1.5f, 0 - 1.5f,  0 - 1.5f, -6 - 1.5f };
        createRotatedPolygons(vertices, 10, 15, 60);

        // FIXME Width too big
        vertices = new float[] {-10, 0,  0, 10,  10, 0,  0, -20};
        BodiedActorBuilder builder = Bodies.newBodiedActor(physicsWorld);
        builder.withDynamicBody().at(110, 30).withAngularVelocityInDegrees(angularVelocity)
              .beginFixture().withPolygonShapeAsActor().withVertices(vertices).endShape().withDensity(0.5f).endFixture()
              .beginFixture().withPolygonShapeAsActor().at(0, -12.5f).withVertices(new float[] {0,0, 20,0, 20,5, 0,5}).endShape().withDensity(0.5f).endFixture();
        createBodiedActor(builder.build());
        builder.dispose();
        
        // Workflow to create a arbitrary non-intersecting bodied polygon
        vertices = new float[] { -3, 0,  0, 3,  3, 0,  1.5f, -1.5f,  0, -6,  -1.5f, -1.5f };
        PolygonActor actor = new PolygonActor(vertices);
        actor.setOrigin(Align.center);
        
        Rectangle polygonBounds = actor.getPolygonBounds();
        short[] triangles = actor.getPolygonTriangles();
        float deltaX = polygonBounds.width / -2 - polygonBounds.x;
        float deltaY = polygonBounds.height / -2 - polygonBounds.y;
        
        BodyBuilder bodyBuilder = Bodies.newBody(physicsWorld);
        bodyBuilder.withDynamicBody().at(110, 60).withLinearVelocity(-5, 0).withAngularVelocityInDegrees(angularVelocity);
        for (int index = 0; index < triangles.length; index += 3) {
            int vertix1 = triangles[index] * 2;
            int vertix2 = triangles[index + 1] * 2;
            int vertix3 = triangles[index + 2] * 2;
            
            bodyBuilder.beginFixture().withPolygonShape()
                .addVertix(vertices[vertix1] + deltaX, vertices[vertix1 + 1] + deltaY)
                .addVertix(vertices[vertix2] + deltaX, vertices[vertix2 + 1] + deltaY)
                .addVertix(vertices[vertix3] + deltaX, vertices[vertix3 + 1] + deltaY)
            .endShape().withDensity(0.5f).endFixture();
        }
        createBodiedActor(bodyBuilder.build(), actor);
        bodyBuilder.dispose();
    }
    
    private void createRotatedPolygons(float[] vertices, float startX, float stepX, float y) {
        float x = startX;
        
        BodiedActorBuilder builder = Bodies.newBodiedActor(physicsWorld);
        builder.type(BodyType.DynamicBody).at(x, y).rotatedByDegrees(0)
            .beginFixture().withPolygonShapeAsActor().withVertices(vertices).endShape().withDensity(0.5f).endFixture();
        createBodiedActor(builder.build());
        builder.dispose();
        
        x += stepX;
        builder = Bodies.newBodiedActor(physicsWorld);
        builder.type(BodyType.DynamicBody).at(x, y).rotatedByDegrees(90)
            .beginFixture().withPolygonShapeAsActor().withVertices(vertices).endShape().withDensity(0.5f).endFixture();
        createBodiedActor(builder.build());
        builder.dispose();

        x += stepX;
        builder = Bodies.newBodiedActor(physicsWorld);
        builder.type(BodyType.DynamicBody).at(x, y).rotatedByDegrees(180)
            .beginFixture().withPolygonShapeAsActor().withVertices(vertices).endShape().withDensity(0.5f).endFixture();
        createBodiedActor(builder.build());
        builder.dispose();
        
        x += stepX;
        builder = Bodies.newBodiedActor(physicsWorld);
        builder.type(BodyType.DynamicBody).at(x, y).rotatedByDegrees(270)
            .beginFixture().withPolygonShapeAsActor().withVertices(vertices).endShape().withDensity(0.5f).endFixture();
        createBodiedActor(builder.build());
        builder.dispose();

        x += stepX * 1.5f;
        builder = Bodies.newBodiedActor(physicsWorld);
        builder.type(BodyType.DynamicBody).at(x, y).withAngularVelocityInDegrees(angularVelocity)
            .beginFixture().withPolygonShapeAsActor().withVertices(vertices).endShape().withDensity(0.5f).endFixture();
        createBodiedActor(builder.build());
        builder.dispose();
    }

    private void initializePhysicsAndStage() {
        Box2D.init();
        
        worldEntity = getWorld().createEntity();
        EntityEdit worldEdit = worldEntity.edit();
        Box2DWorld world = worldEdit.create(Box2DWorld.class).initialize(new Vector2(0, 0));
        physicsWorld = world.get();
        
        Viewport worldViewport = new ScalingViewport(Scaling.fit, worldWidth, worldHeight);
        Stage worldStage = new Stage(worldViewport, renderSystem.getGlobalBatch());
        worldEdit.create(Layer.class).setZIndex(0);
        worldEdit.create(Scene.class).initialize(worldStage);
        worldEdit.create(InputHandler.class).setProcessor(worldStage);

        float[] vertices = new float[] {-1, -1,  -1, 1,  1, 1,  1, -1 };
        Actor background = new PolygonActor(vertices);
        background.setName("Background");
        background.setColor(Color.DARK_GRAY);
        background.setSize(worldWidth, worldHeight);
        worldStage.addActor(background);
        
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Drawable borderImage = new TextureDrawable(pixmap);
        
        float halfWorldWidth = worldWidth / 2;
        float halfWorldHeight = worldHeight / 2;
        float halfBorderSize = borderSize / 2;
        BodiedActorBuilder borderBuilder = Bodies.newBodiedActor(physicsWorld);
        borderBuilder.type(BodyType.StaticBody).at(worldWidth / 2, worldHeight / 2)
            .beginFixture().withBoxShape(worldWidth, borderSize).at(0, halfWorldHeight - halfBorderSize).endShape().withImage(borderImage).withColor(Color.LIGHT_GRAY).endFixture()
            .beginFixture().withBoxShape(borderSize, worldHeight).at(halfWorldWidth - halfBorderSize, 0).endShape().withImage(borderImage).withColor(Color.LIGHT_GRAY).endFixture()
            .beginFixture().withBoxShape(worldWidth, borderSize).at(0, -halfWorldHeight + halfBorderSize).endShape().withImage(borderImage).withColor(Color.LIGHT_GRAY).endFixture()
            .beginFixture().withBoxShape(borderSize, worldWidth).at(-halfWorldWidth + halfBorderSize, 0).endShape().withImage(borderImage).withColor(Color.LIGHT_GRAY).endFixture();
        createBodiedActor(borderBuilder.build());
        borderBuilder.dispose();
        
        debugRenderer.setup(physicsWorld, worldViewport);
        debugRenderer.getRenderer().setDrawCentroids(true);
        worldStage.setDebugAll(true);
    }

    private void createBodiedActor(PooledPair<Body, Actor> bodiedActor) {
        createBodiedActor(bodiedActor.getA(), bodiedActor.getB());
    }

    private void createBodiedActor(Body body, Actor actor) {
        Entity entity = getWorld().createEntity();
        EntityEdit entityEdit = entity.edit();
        entityEdit.create(BodyComponent.class).set(body);
        entityEdit.create(ActorComponent.class).set(actor);
        sceneMapper.get(worldEntity).getStage().addActor(actor);
    }
    
}
