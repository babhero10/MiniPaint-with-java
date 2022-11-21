package backend.struct;

import java.awt.*;
import java.io.Serial;
import java.util.Map;


import static backend.constants.FileConfig.SERIAL_VERSION_UID;

public interface Shape {

    Color DEF_STROKE_COLOR = Color.black;
    Color DEF_FILL_COLOR = Color.black;
    int DEF_STROKE_SIZE = 5;



    /* Set position */
    void setPosition(Point position);

    Point getPosition();

    /* Update shape specific properties (e.g., radius) */
    void setProperties(Map<String, String> properties);

    Map<String, String> getProperties();

    void addProperties(String key, String value);

    /* Colorize */
    void setColor(Color color) ;
    Color getColor();

    void setFillColor(Color color);
    Color getFillColor();

    /* Redraw the shape on the canvas */
    void draw(Graphics canvas);

    Shape copy(Shape shape);

}
