package frontend;

import backend.*;
import backend.Rectangle;
import backend.Shape;

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
        comboBox.removeAllItems();
        String shapeType = "";
        Shape[] shapes = getShapes();
        for (int i = 0; i < shapes.length; i++) {

            if (shapes[i] instanceof Circle) shapeType = "Circle";
            else if (shapes[i] instanceof LineSegment) shapeType = "Line Segment";
            else if (shapes[i] instanceof Rectangle) shapeType = "Rectangle";
            else if (shapes[i] instanceof Square) shapeType = "Square";
            comboBox.addItem(shapeType+(i+1));
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
