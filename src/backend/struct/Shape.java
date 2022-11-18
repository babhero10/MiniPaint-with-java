package backend.struct;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Shape {
    private Point pos;
    private Color strokeColor, fillColor;
    private Map<String, String> properties;

    public static Color DEF_STROKE_COLOR = Color.black;
    public static Color DEF_FILL_COLOR = Color.black;
    public static int DEF_STROKE_SIZE = 5;
    public Shape() {
        pos = new Point(0, 0);
        strokeColor = DEF_STROKE_COLOR;
        fillColor = DEF_FILL_COLOR;
        properties = new HashMap<>();

    }

    /* Set position */
    public void setPosition(Point position) {
        pos = position;
    }

    public Point getPosition() {
        return pos;
    }

    /* Update shape specific properties (e.g., radius) */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    /* Colorize */
    public void setColor(Color color) {
        strokeColor = color;
    }
    public Color getColor() {
        return strokeColor;
    }

    public void setFillColor(Color color) {
        fillColor = color;
    }
    public Color getFillColor() {
        return fillColor;
    }

    /* Redraw the shape on the canvas */
    public abstract void draw(Graphics canvas);
}
