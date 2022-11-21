package backend.struct;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static backend.constants.FileConfig.SERIAL_VERSION_UID;
import static backend.constants.Properties.SET_BORDER_KEY;
import static backend.constants.Properties.SET_FILL_KEY;

public abstract class NormalShape implements Shape, Serializable {
    @Serial
    private static long serialVersionUID = SERIAL_VERSION_UID;

    private Point pos;
    private Color strokeColor, fillColor;
    private Map<String, String> properties;
    public NormalShape() {
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
        fillColor = color;
    }
    public Color getFillColor() {
        return fillColor;
    }

    /* Redraw the shape on the canvas */
    public abstract void draw(Graphics canvas);

    public abstract Shape copy(Shape shape);
}
