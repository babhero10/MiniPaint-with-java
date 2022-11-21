package backend.struct;

import backend.Engine;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static backend.constants.FileConfig.SERIAL_VERSION_UID;

public abstract class Shape implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;
    private Point pos;
    private Color strokeColor, fillColor;
    private Map<String, String> properties;

    public static Color DEF_STROKE_COLOR = Color.black;
    public static Color DEF_FILL_COLOR = Color.black;
    public static int DEF_STROKE_SIZE = 5;
    public static String NAME_KEY = "name";
    public static String PREV_NAME_KEY = "prev_name";
    public static String SET_BORDER_KEY = "set border";
    public static String SET_FILL_KEY = "set fill";
    public Shape() {
        pos = new Point(0, 0);
        strokeColor = DEF_STROKE_COLOR;
        fillColor = DEF_FILL_COLOR;
        properties = new HashMap<>();
        properties.put(SET_BORDER_KEY, "true");
        properties.put(SET_FILL_KEY, "true");
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

    public void addPropertie(String key, String value) {
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
        fillColor = color;
    }
    public Color getFillColor() {
        return fillColor;
    }

    /* Redraw the shape on the canvas */
    public abstract void draw(Graphics canvas);

    public abstract Shape copy(Shape shape);

}
