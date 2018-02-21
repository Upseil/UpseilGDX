package com.upseil.gdx.testbed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.upseil.gdx.scene2d.util.TextColor;

public class WrapColorMarkupTestbed extends TestbedApplication {
    
    public static void main(String[] args) {
        launch(new WrapColorMarkupTestbed());
    }
    
    private Stage stage;
    private BitmapFont font;
    
    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());

        TextColor red = TextColor.byName("RED");
        TextColor green = TextColor.byName("GREEN");
        TextColor blue = TextColor.byName("BLUE");
        
        String text = "This is some long text that will be repeated in different colors, to check if there's an issue with using the "
                      + "color markup language and wrapping.";
        
        Table container = new Table();
        container.setFillParent(true);
        container.pad(10);
        
        FileHandle fontFile = Gdx.files.internal("arial-15.fnt");
        font = new BitmapFont(fontFile);
        font.getData().markupEnabled = true;
        
        LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
        Label label = new Label("", labelStyle);
        label.setWrap(true);
        label.setText(red.asMarkup() + text + " " + green.asMarkup() + text + " " + blue.asMarkup() + text);
        container.add(label).fill().expand();
        
        stage.addActor(container);
    }
    
    @Override
    protected void render(float deltaTime) {
        clearScreen();

        stage.act(deltaTime);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void dispose() {
        font.dispose();
    }
    
}
