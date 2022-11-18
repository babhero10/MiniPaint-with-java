package backend;

import java.awt.*;
import java.util.Map;

public class LineSegment implements Shape{
    private Point startPoint;
    private Color strokeColor;
    private Color fillColor;
    private Map<String, Double> properties;

    public LineSegment() {
        strokeColor = Color.BLACK;
        fillColor = Color.BLACK;
    }

    @Override
    public void setPosition(Point position) {
        this.startPoint = position;
    }

    @Override
    public Point getPosition() {
        return startPoint;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, Double> getProperties() {
        return properties;
    }

    @Override
    public void setColor(Color color) {
        this.strokeColor = color;
    }

    @Override
    public Color getColor() {
        return strokeColor;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void draw(Graphics canvas) {
        double xTo = getProperties().get("xTo");
        double yTo = getProperties().get("yTo");

        canvas.setColor(getColor());
        ((Graphics2D)canvas).setStroke(new BasicStroke(2));
        canvas.drawLine(startPoint.x, startPoint.y, (int) (xTo), (int) (yTo));
    }
}
