package backend.struct;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static backend.constants.FileConfig.SERIAL_VERSION_UID;
import static backend.constants.Properties.SET_BORDER_KEY;
import static backend.constants.Properties.SET_FILL_KEY;

public abstract class OtherShape implements Shape, Serializable {
    private static long serialVersionUID = SERIAL_VERSION_UID;
    private Point startPoint;
    private Color strokeColor;
    private Map<String, String> properties;

    public OtherShape() {
        startPoint = new Point(0, 0);
        strokeColor = DEF_STROKE_COLOR;
        properties = new HashMap<>();
        properties.put(SET_BORDER_KEY, "true");
        properties.put(SET_FILL_KEY, "false");
    }

    /* Set position */
    public void setPosition(Point position) {
        startPoint = position;
    }

    public Point getPosition() {
        return startPoint;
    }

    /* Update shape specific properties (e.g., radius) */
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void addProperties(String key, String value) {
        this.properties.put(key, value);
    }

    /* Colorize */
    public void setColor(Color color) {
        strokeColor = color;
    }
    public Color getColor() {
        return strokeColor;
    }

    public void setFillColor(Color color) {

    }
    public Color getFillColor() {
        return null;
    }

    /* Redraw the shape on the canvas */
    public abstract void draw(Graphics canvas);

    public abstract Shape copy(Shape shape);
}