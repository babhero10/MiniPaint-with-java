package backend;

import java.awt.*;
import java.util.Map;

public class Circle implements Shape {

    private Point center;
    private Color strokeColor;
    private Color fillColor;
    private Map<String, Double> properties;

    public Circle() {
        strokeColor = Color.BLACK;
        fillColor = Color.BLACK;
    }

    @Override
    public void setPosition(Point position) {
        double radius = getProperties().get("radius");
        this.center = position;
        this.center.x = (int) (position.x - radius);
        this.center.y = (int) (position.y - radius);
    }

    @Override
    public Point getPosition() {
        return center;
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
        double r = getProperties().get("radius");
        ((Graphics2D) canvas).setStroke(new BasicStroke(5));
        canvas.setColor(getColor());
        canvas.drawOval(center.x, center.y, (int) (r * 2), (int) (r * 2));
        canvas.setColor(getFillColor());
        canvas.fillOval(center.x, center.y, (int) (r * 2), (int) (r * 2));
    }
}
