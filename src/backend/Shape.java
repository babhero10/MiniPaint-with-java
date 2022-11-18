package backend;

import java.awt.*;
import java.util.Map;

public interface Shape {
    /* Set position */
    void setPosition(Point position);
    Point getPosition();
    /* Update shape specific properties (e.g., radius) */
    void setProperties(Map<String, Double> properties);
    Map<String, Double> getProperties();
    /* Colorize */
    void setColor(Color color);
    Color getColor();
    void setFillColor(Color color);
    Color getFillColor();
    /* Redraw the shape on the canvas */
    void draw(Graphics canvas);
}
