package backend.drawableshapes;

import backend.struct.NormalShape;
import backend.struct.Shape;

import java.awt.*;
import java.util.HashMap;
import static backend.constants.Properties.*;

public class Circle extends NormalShape {

    private int radius;
    public Circle() {
        super();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void draw(Graphics canvas) {
        ((Graphics2D) canvas).setStroke(new BasicStroke(DEF_STROKE_SIZE));

        if (getProperties().get(SET_BORDER_KEY).equals("true")) {
            canvas.setColor(getColor());
            canvas.drawOval(getPosition().x - getRadius(), getPosition().y - getRadius(),
                    (getRadius() * 2), (getRadius() * 2));
        }
        if (getProperties().get(SET_FILL_KEY).equals("true")) {
            canvas.setColor(getFillColor());
            canvas.fillOval(getPosition().x - getRadius(), getPosition().y - getRadius(),
                    (getRadius() * 2), (getRadius() * 2));
        }
    }

    @Override
    public Shape copy(Shape shape) {
        Circle c = new Circle();
        c.setRadius(getRadius());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        c.setPosition((Point) getPosition().clone());
        c.setProperties(new HashMap<>(getProperties()));
        return c;
    }

}
