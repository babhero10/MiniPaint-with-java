package backend.drawableshapes;

import backend.struct.Shape;

import java.awt.*;

public class LineSegment extends Shape {
    private Point endPoint;

    public LineSegment() {
        super();
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public void setFillColor(Color color) {
        setFillColor(getColor());
    }

    @Override
    public Color getFillColor() {
        return getColor();
    }

    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(getColor());
        ((Graphics2D)canvas).setStroke(new BasicStroke(DEF_STROKE_SIZE));
        canvas.drawLine(getPosition().x, getPosition().y, getEndPoint().x, getEndPoint().y);
    }
}
