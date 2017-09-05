package com.upseil.gdx.viewport;

import com.badlogic.gdx.math.Rectangle;

public class GridDivider implements ScreenDivider {
    
    private int columns;
    private int rows;
    private int column;
    private int row;

    public void set(int columns, int rows, int column, int row) {
        this.columns = columns;
        this.rows = rows;
        this.column = column;
        this.row = row;
        
    }
    
    @Override
    public void getScreenPart(Rectangle screen) {
        float xUnit = screen.width / columns;
        float yUnit = screen.height / rows;
        
        float newX = screen.x + (xUnit * column);
        float newY = screen.y + screen.height - (yUnit * (row + 1));
        screen.set(newX, newY, xUnit, yUnit);
    }
    
}
