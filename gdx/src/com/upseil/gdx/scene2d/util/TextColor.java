package com.upseil.gdx.scene2d.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;

public class TextColor {
    
    public static final TextColor None = new TextColor(null);

    private static final ObjectMap<String, TextColor> byNameCache = new ObjectMap<>();
    
    public static TextColor byName(String name) {
        if (name == null) {
            return None;
        }
        if (Colors.get(name) == null) {
            new IllegalArgumentException("No color found with name " + name);
        }
        
        TextColor textColor = byNameCache.get(name);
        if (textColor == null) {
            textColor = new TextColor(name);
            byNameCache.put(name, textColor);
        }
        return textColor;
    }
    
    private final String name;
    private final String markup;

    private TextColor(String name) {
        this.name = name;
        markup = name == null ? "" : "[" + name + "]";
    }
    
    public String getColorName() {
        return name;
    }
    
    public Color getColor() {
        return name == null ? null : Colors.get(name);
    }
    
    public String asMarkup() {
        return markup;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TextColor other = (TextColor) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Calculates the given colors intensity and returns black, if it is higher than the given
     * threshold and white otherwise. Given a reasonable threshold, the returned color should
     * have a high contrast, ensuring good readability. The recommended threshold is <code>149</code>.
     * 
     * @param color The {@link Color} of the background
     * @param threshold The intensity threshold [0 - 255] that determines if black or white is returned.
     *                  <code>149</code> is recommended.
     * @return Black or white, whichever has a higher contrast
     * 
     * @see Uses the simple variant described in
     *      <a href="https://stackoverflow.com/a/3943023">https://stackoverflow.com/a/3943023</a>.
     */
    public static TextColor forBackground(Color color, int threshold) {
        return forBackground(color, threshold / 255f);
    }
    
    /**
     * Calculates the given colors intensity and returns black, if it is higher than the given
     * threshold and white otherwise. Given a reasonable threshold, the returned color should
     * have a high contrast, ensuring good readability. The recommended threshold is <code>0.58</code>.
     * 
     * @param color The {@link Color} of the background
     * @param threshold The intensity threshold [0 - 1] that determines if black or white is returned.
     *                  <code>0.58</code> is recommended.
     * @return Black or white, whichever has a higher contrast
     * 
     * @see Uses the simple variant described in
     *      <a href="https://stackoverflow.com/a/3943023">https://stackoverflow.com/a/3943023</a>.
     */
    public static TextColor forBackground(Color color, float threshold) {
        return forBackground(color.r, color.g, color.b, threshold);
    }
    
    /**
     * Calculates the given colors intensity and returns black, if it is higher than the given
     * threshold and white otherwise. Given a reasonable threshold, the returned color should
     * have a high contrast, ensuring good readability. The recommended threshold is <code>149</code>.
     * 
     * @param grayscale The background color as grayscale [0 - 255]
     * @param threshold The intensity threshold [0 - 255] that determines if black or white is returned.
     *                  <code>149</code> is recommended.
     * @return Black or white, whichever has a higher contrast
     * 
     * @see Uses the simple variant described in
     *      <a href="https://stackoverflow.com/a/3943023">https://stackoverflow.com/a/3943023</a>.
     */
    public static TextColor forBackground(int grayscale, int threshold) {
        return forBackground(grayscale, grayscale, grayscale, threshold);
    }
    
    /**
     * Calculates the given colors intensity and returns black, if it is higher than the given
     * threshold and white otherwise. Given a reasonable threshold, the returned color should
     * have a high contrast, ensuring good readability. The recommended threshold is <code>149</code>.
     * 
     * @param r The red value of the background [0 - 255]
     * @param g The green value of the background [0 - 255]
     * @param b The blue value of the background [0 - 255]
     * @param threshold The intensity threshold [0 - 255] that determines if black or white is returned.
     *                  <code>149</code> is recommended.
     * @return Black or white, whichever has a higher contrast
     * 
     * @see Uses the simple variant described in
     *      <a href="https://stackoverflow.com/a/3943023">https://stackoverflow.com/a/3943023</a>.
     */
    public static TextColor forBackground(int r, int g, int b, int threshold) {
        return forBackground(r / 255f, g / 255f, b / 255f, threshold / 255f);
    }
    
    /**
     * Calculates the given colors intensity and returns black, if it is higher than the given
     * threshold and white otherwise. Given a reasonable threshold, the returned color should
     * have a high contrast, ensuring good readability. The recommended threshold is <code>0.58</code>.
     * 
     * @param grayscale The background color as grayscale [0 - 1]
     * @param threshold The intensity threshold [0 - 1] that determines if black or white is returned.
     *                  <code>0.58</code> is recommended.
     * @return Black or white, whichever has a higher contrast
     * 
     * @see Uses the simple variant described in
     *      <a href="https://stackoverflow.com/a/3943023">https://stackoverflow.com/a/3943023</a>.
     */
    public static TextColor forBackground(float grayscale, float threshold) {
        return forBackground(grayscale, grayscale, grayscale, threshold);
    }
    
    /**
     * Calculates the given colors intensity and returns black, if it is higher than the given
     * threshold and white otherwise. Given a reasonable threshold, the returned color should
     * have a high contrast, ensuring good readability. The recommended threshold is <code>0.58</code>.
     * 
     * @param r The red value of the background [0 - 1]
     * @param g The green value of the background [0 - 1]
     * @param b The blue value of the background [0 - 1]
     * @param threshold The intensity threshold [0 - 1] that determines if black or white is returned.
     *                  <code>0.58</code> is recommended.
     * @return Black or white, whichever has a higher contrast
     * 
     * @see Uses the simple variant described in
     *      <a href="https://stackoverflow.com/a/3943023">https://stackoverflow.com/a/3943023</a>.
     */
    public static TextColor forBackground(float r, float g, float b, float threshold) {
        float intensity = MathUtils.clamp(r, 0, 1) * 0.299f + MathUtils.clamp(g, 0, 1) * 0.587f + MathUtils.clamp(b, 0, 1) * 0.114f;
        return intensity <= MathUtils.clamp(threshold, 0, 1) ? byName("WHITE") : byName("BLACK");
    }
    
}