package backend.drawableshapes;

import backend.struct.Shape;

import java.awt.*;
import java.util.HashMap;

public class LineSegment extends Shape {
    private Point endPoint;

    public LineSegment() {
        super();
        getProperties().put(SET_FILL_KEY, "false");
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public void setFillColor(Color color) {

    }

    @Override
    public Color getFillColor() {
        return null;
    }

    @Override
    public void draw(Graphics canvas) {
        ((Graphics2D)canvas).setStroke(new BasicStroke(DEF_STROKE_SIZE));
        canvas.setColor(getColor());
        canvas.drawLine(getPosition().x, getPosition().y, getEndPoint().x, getEndPoint().y);
    }

    @Override
    public Shape copy(Shape shape) {
        LineSegment c = new LineSegment();
        c.setEndPoint(getEndPoint());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        c.setPosition((Point) getPosition().clone());
        c.setProperties(new HashMap<>(getProperties()));
        return c;
    }
}
