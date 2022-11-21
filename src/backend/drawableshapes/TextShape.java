package backend.drawableshapes;

import backend.struct.OtherShape;
import backend.struct.Shape;

import java.awt.*;
import java.util.HashMap;


public class TextShape extends OtherShape {

    private String text;
    private int textSize;

    public TextShape() {
        super();
        setTextSize(15);
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public void draw(Graphics canvas) {
        ((Graphics2D)canvas).setStroke(new BasicStroke(DEF_STROKE_SIZE));
        canvas.setFont(new Font("Monospaced", Font.BOLD, getTextSize()));
        canvas.setColor(getColor());
        canvas.drawString(getText(), getPosition().x + getTextSize(), getPosition().y + getTextSize());
    }

    @Override
    public Shape copy(Shape shape) {
        TextShape c = new TextShape();
        c.setText(getText());
        c.setTextSize(getTextSize());
        c.setColor(getColor());
        c.setFillColor(getFillColor());
        c.setPosition((Point) getPosition().clone());
        c.setProperties(new HashMap<>(getProperties()));
        return c;
    }
}