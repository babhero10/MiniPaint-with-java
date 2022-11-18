package frontend;

import javax.swing.*;
import java.awt.*;

// 1150 638

public class MainFrame {
    private JPanel main_panel;
    private JComboBox shapesComb;
    private JButton colorizeBtn;
    private JButton deleteBtn;
    private JButton circleBtn;
    private JButton lineSegmentBtn;
    private JButton squareBtn;
    private JButton rectangleBtn;
    public JPanel drawingPanel;
    public static JFrame frame;

    public MainFrame() {

        shapesComb.setFocusable(false);
        circleBtn.setFocusable(false);
        rectangleBtn.setFocusable(false);
        squareBtn.setFocusable(false);
        lineSegmentBtn.setFocusable(false);
        colorizeBtn.setFocusable(false);
        deleteBtn.setFocusable(false);

        circleBtn.addActionListener(e -> drawCircle());
        lineSegmentBtn.addActionListener(e -> drawLineSegment());
        rectangleBtn.addActionListener(e -> drawRectangle());
        squareBtn.addActionListener(e -> drawSquare());

        deleteBtn.addActionListener(e -> deleteShape());
        colorizeBtn.addActionListener(e -> changeColor());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

        frame = new JFrame("Vector Drawing Application");
        frame.setContentPane(new MainFrame().main_panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void drawCircle() {
        new CircleWindow(frame, (Engine) drawingPanel);
        ((Engine) drawingPanel).refreshComboBox(shapesComb);
    }

    void drawLineSegment() {
        new LineSegmentWindow(frame, (Engine) drawingPanel);
        ((Engine) drawingPanel).refreshComboBox(shapesComb);
    }

    void drawRectangle() {
        new RectangleWindow(frame, (Engine) drawingPanel);
        ((Engine) drawingPanel).refreshComboBox(shapesComb);
    }

    void drawSquare() {
        new SquareWindow(frame, (Engine) drawingPanel);
        ((Engine) drawingPanel).refreshComboBox(shapesComb);
    }

    void deleteShape() {
        if (shapesComb.getSelectedIndex() == -1) return;
        Engine e = (Engine) drawingPanel;
        e.removeShape(e.getShapes()[shapesComb.getSelectedIndex()]);
        e.refreshComboBox(shapesComb);


        e.refresh(drawingPanel.getGraphics());
        drawingPanel.paint(drawingPanel.getGraphics());
    }

    void changeColor() {
        if (shapesComb.getSelectedIndex() == -1) return;
        Engine e = (Engine) drawingPanel;
        int index = shapesComb.getSelectedIndex();
        Color[] c = new ChangeColor(frame).showDialog(e.getShape(index).getColor(), e.getShape(index).getFillColor());
        if (c[0] != null) e.getShape(index).setColor(c[0]);
        if (c[1] != null) e.getShape(index).setFillColor(c[1]);
        e.refresh(drawingPanel.getGraphics());
    }

    public void createUIComponents() {
        this.drawingPanel = new Engine();
    }
}
