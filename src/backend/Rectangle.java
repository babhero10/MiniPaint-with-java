package backend;

import java.awt.*;
import java.util.Map;

public class Rectangle implements Shape{
    private Point corner;
    private Color strokeColor;
    private Color fillColor;
    private Map<String, Double> properties;

    public Rectangle() {
        strokeColor = Color.BLACK;
        fillColor = Color.BLACK;
    }

    @Override
    public void setPosition(Point position) {
        this.corner = position;
    }

    @Override
    public Point getPosition() {
        return corner;
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
        double width = getProperties().get("width");
        double height = getProperties().get("height");

        ((Graphics2D) canvas).setStroke(new BasicStroke(5));
        canvas.setColor(getColor());
        canvas.drawRect(corner.x, corner.y, (int) (width), (int) (height));
        canvas.setColor(getFillColor());
        canvas.fillRect(corner.x, corner.y, (int) (width), (int) (height));
    }
}
