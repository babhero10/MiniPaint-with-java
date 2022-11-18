package frontend;

import backend.errors.InvalidName;
import backend.struct.Shape;
import backend.struct.DrawingEngine;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Engine extends JPanel implements DrawingEngine {

    private final Vector<Shape> shapes;

    public Engine() {
        super();
        shapes = new Vector<>();
    }

    public Shape getShape(int index) {
        return shapes.get(index);
    }

    public void refreshComboBox(JComboBox comboBox) {
        if (comboBox == null) return;

        comboBox.removeAllItems();
        Shape[] shapes = getShapes();
        for (Shape shape : shapes) {
            comboBox.addItem(shape.getProperties().get("name"));
        }
    }

    public void checkShapeName(String shapeName) throws InvalidName {
        for (Shape s : getShapes()) {
            if (s.getProperties().get("name").equals(shapeName)) {
                throw new InvalidName();
            }
        }
    }

    @Override
    public void addShape(Shape shape) {
        shapes.add(shape);
        paint(this.getGraphics());
    }

    @Override
    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    @Override
    public Shape[] getShapes() {
        return shapes.toArray(new Shape[0]);
    }

    @Override
    public void refresh(Graphics canvas) {
        paint(canvas);

        for (Shape s : getShapes()) {
            s.draw(canvas);
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
