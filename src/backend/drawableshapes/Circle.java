package backend.drawableshapes;

import backend.struct.Shape;

import java.awt.*;
import java.util.Map;

public class Circle extends Shape {

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
        canvas.setColor(getColor());

        canvas.drawOval(getPosition().x - getRadius(), getPosition().y - getRadius(),
                        (getRadius() * 2), (getRadius() * 2));
        canvas.setColor(getFillColor());
        canvas.fillOval(getPosition().x - getRadius(), getPosition().y - getRadius(),
                        (getRadius() * 2), (getRadius() * 2));
    }
}
