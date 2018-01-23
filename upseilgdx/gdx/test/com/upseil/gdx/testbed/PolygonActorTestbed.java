package com.upseil.gdx.testbed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.upseil.gdx.scene2d.PolygonActor;

public class PolygonActorTestbed extends TestbedApplication {
    
    private PolygonSpriteBatch stageBatch;
    private Stage stage;
    
    @Override
    public void create() {
        stageBatch = new PolygonSpriteBatch();

        Viewport viewport = new ScalingViewport(Scaling.fit, 160, 90);
        stage = new Stage(viewport, stageBatch);
        stage.setDebugAll(true);

        float[] vertices = new float[] {-1, -1,  -1, 1,  1, 1,  1, -1 };
        Actor background = new PolygonActor(vertices);
        background.setName("Background");
        background.setColor(Color.DARK_GRAY);
        background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        stage.addActor(background);
        
        vertices = new float[] {-10, 0,  -2.5f, 2.5f,  0, 10,  2.5f, 2.5f,  10, 0,  2.5f, -2.5f,  0, -10,  -2.5f, -2.5f};
        Actor star = new PolygonActor(vertices);
        star.setName("Star");
        star.setColor(Color.WHITE);
        star.setPosition(15, 60);
        star.setSize(15, 15);
        star.setOrigin(Align.center);
        star.addAction(Actions.forever(Actions.rotateBy(360, 10)));
        star.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 2), Actions.scaleTo(1.5f, 1.5f, 2))));
//        star.addAction(Actions.forever(Actions.sequence(Actions.sizeTo(10, 10, 2), Actions.sizeTo(30, 30, 2))));
        stage.addActor(star);
        
        vertices = new float[] {-10, 0, 0, 10, 10, 0, 0, -20};
        Actor diamond = new PolygonActor(vertices);
        diamond.setName("Diamond");
        diamond.setColor(Color.VIOLET);
        diamond.setPosition(55, 55);
        diamond.setOrigin(Align.center);
        diamond.addAction(Actions.forever(Actions.rotateBy(360, 10)));
        diamond.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 2), Actions.scaleTo(1.5f, 1.5f, 2))));
        stage.addActor(diamond);
        
        vertices = new float[] {-10, -10, 0, 10, 10, -10};
        Actor triangle = new PolygonActor(vertices);
        triangle.setName("Triangle");
        triangle.setColor(Color.CYAN);
        triangle.setPosition(95, 55);
        triangle.setOrigin(Align.center);
        triangle.addAction(Actions.forever(Actions.rotateBy(360, 10)));
        triangle.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 2), Actions.scaleTo(1.5f, 1.5f, 2))));
        stage.addActor(triangle);
        
        vertices = new float[] {-3, 0,  -10, 0,  -8, 5,  8, 5,  10, 0,  3, 0, // Head
                                2, -10,  1.25f, -15,  0, -20,  -1.25f, -15,  -2, -10}; // Needle
        Actor pin = new PolygonActor(vertices);
        pin.setName("Pin");
        pin.setColor(Color.RED);
        pin.setPosition(25, 10);
        pin.setOrigin(Align.center);
        pin.addAction(Actions.forever(Actions.rotateBy(360, 10)));
        pin.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 2), Actions.scaleTo(1.5f, 1.5f, 2))));
        stage.addActor(pin);
        
        vertices = new float[] {-10, 0, 0, 10, 10, 15, 25, 0, 0, -20};
        Actor polygon = new PolygonActor(vertices);
        polygon.setName("Polygon");
        polygon.setColor(Color.BLUE);
        polygon.setPosition(75, 10);
        polygon.setOrigin(Align.center);
        polygon.addAction(Actions.forever(Actions.rotateBy(360, 10)));
        polygon.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 2), Actions.scaleTo(1.5f, 1.5f, 2))));
        stage.addActor(polygon);
        
        Gdx.input.setInputProcessor(stage);
        ClickListener echoNameOnClick = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println(event.getTarget().getName());
            }
        };
        stage.getActors().forEach(actor -> actor.addListener(echoNameOnClick));
    }
    
    @Override
    public void render() {
        clearScreen();
        
        stage.draw();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 0.5f));
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void dispose() {
        stage.dispose();
        stageBatch.dispose();
    }
    
}
