package com.upseil.gdx.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.upseil.gdx.util.GDXArrays;

public class PolygonActor extends ChangeNotifingActor {
    
    public static final int VertexSize = 2 + 1 + 2;
    
    private static final EarClippingTriangulator Triangulator = new EarClippingTriangulator();
    private static final Vector2 tmp = new Vector2();
    
    private static final TextureRegion NullTexture = createNullTexture();
    private static TextureRegion createNullTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        return new TextureRegion(new Texture(new PixmapTextureData(pixmap, null, false, true, true)));
    }
    
    private PolygonRegion polygon;
    private float[] packedVertices;
    private boolean dirty;
    private final Rectangle polygonBounds;
    
    private boolean customSpriteBatch;
    private PolygonSpriteBatch spriteBatch;

    public PolygonActor(Skin skin, String textureRegionName, Vector2[] vertices) {
        this(skin, textureRegionName, vertices, null);
    }

    public PolygonActor(Skin skin, String textureRegionName, Vector2[] vertices, PolygonSpriteBatch spriteBatch) {
        this(skin, textureRegionName, GDXArrays.flattenToArray(vertices), spriteBatch);
    }
    
    public PolygonActor(Skin skin, String textureRegionName, float[] vertices) {
        this(skin, textureRegionName, vertices, null);
    }
    
    public PolygonActor(Skin skin, String textureRegionName, float[] vertices, PolygonSpriteBatch spriteBatch) {
        this(skin.getRegion(textureRegionName), vertices, spriteBatch);
    }

    public PolygonActor(Vector2[] vertices) {
        this(null, vertices);
    }

    public PolygonActor(TextureRegion texture, Vector2[] vertices) {
        this(texture == null ? NullTexture : texture, vertices, null);
    }

    public PolygonActor(Vector2[] vertices, PolygonSpriteBatch spriteBatch) {
        this(null, vertices, spriteBatch);
    }

    public PolygonActor(TextureRegion texture, Vector2[] vertices, PolygonSpriteBatch spriteBatch) {
        this(texture == null ? NullTexture : texture, GDXArrays.flattenToArray(vertices), spriteBatch);
    }

    public PolygonActor(float[] vertices) {
        this(null, vertices);
    }

    public PolygonActor(TextureRegion texture, float[] vertices) {
        this(texture == null ? NullTexture : texture, vertices, null);
    }

    public PolygonActor(TextureRegion texture, float[] vertices, PolygonSpriteBatch spriteBatch) {
        this(new PolygonRegion(texture, vertices, Triangulator.computeTriangles(vertices).toArray()));
    }

    public PolygonActor(PolygonRegion polygonRegion) {
        this(polygonRegion, null);
    }

    public PolygonActor(PolygonRegion polygonRegion, PolygonSpriteBatch spriteBatch) {
        this.polygon = polygonRegion;
        setSpriteBatch(spriteBatch);
        setMonitorColor(true);
        
        float[] polygonVertices = polygon.getVertices();
        float[] textureCoords = polygon.getTextureCoords();
        packedVertices = new float[(polygonVertices.length / 2) * VertexSize];
        
        float colorFloat = getColor().toFloatBits();
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        for (int i = 0, v = 2; i < polygonVertices.length; i += 2, v += VertexSize) {
            packedVertices[v] = colorFloat;
            packedVertices[v + 1] = textureCoords[i];
            packedVertices[v + 2] = textureCoords[i + 1];

            float x = polygonVertices[i];
            float y = polygonVertices[i + 1];
            if (minX > x) minX = x;
            if (maxX < x) maxX = x;
            if (minY > y) minY = y;
            if (maxY < y) maxY = y;
        }

        polygonBounds = new Rectangle(minX, minY, maxX - minX, maxY - minY);
        setSize(polygonBounds.width, polygonBounds.height);
    }
    
    public void setSpriteBatch(PolygonSpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        customSpriteBatch = spriteBatch != null;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!isVisible()) return;
        
        if (spriteBatch == null || (!customSpriteBatch && spriteBatch != batch)) {
            if (!(batch instanceof PolygonSpriteBatch)) {
                throw new IllegalStateException("Unable to draw, because no " + PolygonSpriteBatch.class.getSimpleName()
                        + " has been given by creation and the stages " + Batch.class.getSimpleName() + " is invalid");
            }
            spriteBatch = (PolygonSpriteBatch) batch;
        }
        
        float oldAlpha = -1;
        if (!MathUtils.isEqual(parentAlpha, 1)) {
            Color color = getColor();
            if (!MathUtils.isEqual(color.a, 0)) {
                oldAlpha = color.a;
                color.a *= parentAlpha;
                colorChanged();
            }
        }
        
        float[] vertices = getPackedVertices();
        short[] triangles = polygon.getTriangles();
        spriteBatch.draw(polygon.getRegion().getTexture(), vertices, 0, vertices.length, triangles, 0, triangles.length);
        
        if (!MathUtils.isEqual(oldAlpha, -1)) {
            getColor().a = oldAlpha;
            colorChanged();
        }
    }
    
    private float[] getPackedVertices() {
        if (!dirty) return packedVertices;
        
        // FIXME Changed Size and Origin breaks polygon positioning
        
        float width = getWidth();
        float height = getHeight();

        float polygonScaleX = width / polygonBounds.width;
        float polygonScaleY = height / polygonBounds.height;
        float polygonDeltaX = -polygonBounds.x;
        float polygonDeltaY = -polygonBounds.y;

        float originX = getOriginX();
        float originY = getOriginY();
        float transformationOriginX = polygonDeltaX - originX;
        float transformationOriginY = polygonDeltaY - originY;
        float worldOriginX = getX() + originX;
        float worldOriginY = getY() + originY;
        
        float scaleX = getScaleX() * polygonScaleX;
        float scaleY = getScaleY() * polygonScaleY;
        float cos = MathUtils.cosDeg(getRotation());
        float sin = MathUtils.sinDeg(getRotation());

        float tmpX, tmpY;
        float[] polygonVertices = polygon.getVertices();
        for (int i = 0, v = 0; i < polygonVertices.length; i += 2, v += 5) {
            tmpX = scaleX * (polygonVertices[i] + transformationOriginX);
            tmpY = scaleY * (polygonVertices[i + 1] + transformationOriginY);
            packedVertices[v] = cos * tmpX - sin * tmpY + worldOriginX;
            packedVertices[v + 1] = sin * tmpX + cos * tmpY + worldOriginY;
        }
        
        dirty = false;
        return packedVertices;
    }
    
    @Override
    public Actor hit(float x, float y, boolean touchable) {
        Actor hit = super.hit(x, y, touchable);
        if (hit == null || !contains(x, y)) return null;
        return hit;
    }
    
    public boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }
    
    public boolean contains(float x, float y) {
        float[] vertices = getPackedVertices();
        localToParentCoordinates(tmp.set(x, y));
        boolean contains = false;
        int j = vertices.length - VertexSize;
        for (int i = 0; i < vertices.length; i += VertexSize) {
            float yi = vertices[i + 1];
            float yj = vertices[j + 1];
            if ((yi < tmp.y && yj >= tmp.y) || (yj < tmp.y && yi >= tmp.y)) {
                float xi = vertices[i];
                if (xi + (tmp.y - yi) / (yj - yi) * (vertices[j] - xi) < tmp.x) contains = !contains;
            }
            j = i;
        }
        return contains;
    }

    @Override
    protected void positionChanged() {
        if (!dirty) {
            float deltaX = getX() - previousX;
            float deltaY = getY() - previousY;
            for (int v = 0; v < packedVertices.length; v += VertexSize) {
                packedVertices[v] += deltaX;
                packedVertices[v + 1] += deltaY;
            }
        }
        previousX = getX();
        previousY = getY();
    }
    
    @Override
    protected void colorChanged() {
        float colorFloat = getColor().toFloatBits();
        for (int v = 2; v < packedVertices.length; v += VertexSize) {
            packedVertices[v] = colorFloat;
        }
    }
    
    @Override
    protected void sizeChanged() {
        dirty = true;
    }
    
    @Override
    protected void rotationChanged() {
        dirty = true;
    }
    
    @Override
    protected void originChanged() {
        dirty = true;
    }
    
    @Override
    protected void scaleChanged() {
        dirty = true;
    }
    
    private float previousX;
    private float previousY;
    
    @Override
    public void setBounds(float x, float y, float width, float height) {
        dirty = true; // Prevents vertex repositioning, since the positionChanged() is called before sizeChanged()
        super.setBounds(x, y, width, height);
    }
    
    public float[] getPolygonVertices() {
        return polygon.getVertices();
    }
    
    public short[] getPolygonTriangles() {
        return polygon.getTriangles();
    }
    
    public Rectangle getPolygonBounds() {
        return polygonBounds;
    }
    
}
