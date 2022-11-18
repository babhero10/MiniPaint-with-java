package frontend;


import backend.drawableshapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RectangleWindow extends JDialog {

    private final Engine engine;
    private final JTextField xPosField, yPosField, widthField, heightField;

    private final Rectangle rectangle;

    private final JLabel colorLabel, fillColorLabel;

    public RectangleWindow(JFrame parent, Engine engine) {
        super(parent, "Rectangle probleties", ModalityType.DOCUMENT_MODAL);
        this.engine = engine;
        rectangle = new Rectangle();

        this.setLayout(null);
        this.setSize(500, 525);

        JLabel xPosLabel = new JLabel("X position");
        xPosField = new JTextField();

        JLabel yPosLabel = new JLabel("Y position");
        yPosField = new JTextField();

        JLabel widthLabel = new JLabel("Width ");
        widthField = new JTextField();

        JLabel heightLabel = new JLabel("Height ");
        heightField = new JTextField();

        colorLabel = new JLabel("  ");
        JButton colorBtn = new JButton("Stroke");

        fillColorLabel = new JLabel("  ");
        JButton fillColorBtn = new JButton("Fill");

        JButton drawBtn = new JButton("Draw");

        xPosLabel.setBounds(50, 50, 100, 50);
        xPosField.setBounds(150, 50, 250, 50);

        yPosLabel.setBounds(50, 50*2, 100, 50);
        yPosField.setBounds(150, 50*2, 250, 50);

        widthLabel.setBounds(50, 50*3, 100, 50);
        widthField.setBounds(150, 50*3 , 250, 50);

        heightLabel.setBounds(50, 50*4, 100, 50);
        heightField.setBounds(150, 50*4 , 250, 50);

        colorBtn.setBounds(150, 50*5 + 20, 100, 50);
        colorLabel.setBounds(260, 50*5 + 20, 125, 50);

        fillColorBtn.setBounds(150, 50*6 + 20, 100, 50);
        fillColorLabel.setBounds(260, 50*6 + 20, 125, 50);

        drawBtn.setBounds(225,50*7 + 50,100,50);

        colorBtn.setFocusable(false);
        colorBtn.addActionListener(e -> setColor());
        fillColorBtn.setFocusable(false);
        fillColorBtn.addActionListener(e -> setFillColor());

        drawBtn.setFocusable(false);
        drawBtn.addActionListener(actionEvent -> draw());
        colorLabel.setOpaque(true);
        colorLabel.setBackground(Color.BLACK);

        fillColorLabel.setOpaque(true);
        fillColorLabel.setBackground(Color.BLACK);

        this.add(xPosLabel);
        this.add(xPosField);
        this.add(yPosLabel);
        this.add(yPosField);
        this.add(widthLabel);
        this.add(widthField);
        this.add(heightLabel);
        this.add(heightField);
        this.add(colorLabel);
        this.add(colorBtn);
        this.add(fillColorLabel);
        this.add(fillColorBtn);
        this.add(drawBtn);
        this.getRootPane().setDefaultButton(drawBtn);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void setColor(){
        Color color = JColorChooser.showDialog(this, "Pick a color", Color.BLACK);
        colorLabel.setBackground(color);
        rectangle.setColor(color);
    }

    public void setFillColor(){
        Color color = JColorChooser.showDialog(this, "Pick a color", Color.BLACK);
        fillColorLabel.setBackground(color);
        rectangle.setFillColor(color);
    }

    public void draw() {
        int x, y;
        double width, height;

        try {
            x = Integer.parseInt(xPosField.getText());
            y = Integer.parseInt(yPosField.getText());
            width = Double.parseDouble(widthField.getText());
            height = Double.parseDouble(heightField.getText());

            Map<String, Double> p = new HashMap<>();
            p.put("width", width);
            p.put("height", height);
            rectangle.setProperties(p);
            rectangle.setPosition(new Point(x, y));
            engine.addShape(rectangle);

            this.dispose();
            engine.refresh(engine.getGraphics());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid data", "Failed!",
                                    JOptionPane.WARNING_MESSAGE);
        }
    }

}


