package backend.drawableshapes;

import backend.struct.Shape;

import java.awt.*;
import java.util.Map;

public class Rectangle extends Shape {

    private int width;
    private int height;

    public Rectangle() {
        super();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width >= 0)
            this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height >= 0)
            this.height = height;
    }

    @Override
    public void draw(Graphics canvas) {

        ((Graphics2D) canvas).setStroke(new BasicStroke(DEF_STROKE_SIZE));
        canvas.setColor(getColor());
        canvas.drawRect(getPosition().x, getPosition().y, width, height);
        canvas.setColor(getFillColor());
        canvas.fillRect(getPosition().x, getPosition().y, width, height);
    }
}
