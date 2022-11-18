package frontend;


import backend.drawableshapes.LineSegment;
import backend.errors.InvalidName;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LineSegmentWindow extends JDialog {

    private final Engine engine;
    private final JTextField nameField, xPosField, yPosField, xPosToField, yPosToField;

    private final LineSegment lineSegment;

    private final JLabel colorLabel;

    public LineSegmentWindow(JFrame parent, Engine engine) {
        super(parent, "Line probleties", ModalityType.DOCUMENT_MODAL);
        this.engine = engine;
        lineSegment = new LineSegment();

        this.setLayout(null);
        this.setSize(500, 525);

        JLabel shapeNameLabel = new JLabel("Shape name");
        nameField = new JTextField();

        JLabel xPosLabel = new JLabel("X From");
        xPosField = new JTextField();

        JLabel yPosLabel = new JLabel("Y From");
        yPosField = new JTextField();

        JLabel xPosToLabel = new JLabel("X To ");
        xPosToField = new JTextField();

        JLabel yPosToLabel = new JLabel("Y To ");
        yPosToField = new JTextField();

        colorLabel = new JLabel("  ");
        JButton colorBtn = new JButton("Color");

        JButton drawBtn = new JButton("Draw");

        shapeNameLabel.setBounds(50, 50, 100, 50);
        nameField.setBounds(150, 50, 250, 50);

        xPosLabel.setBounds(50, 50*2, 100, 50);
        xPosField.setBounds(150, 50*2, 250, 50);

        yPosLabel.setBounds(50, 50*3, 100, 50);
        yPosField.setBounds(150, 50*3, 250, 50);

        xPosToLabel.setBounds(50, 50*4, 100, 50);
        xPosToField.setBounds(150, 50*4 , 250, 50);

        yPosToLabel.setBounds(50, 50*5, 100, 50);
        yPosToField.setBounds(150, 50*5 , 250, 50);

        colorBtn.setBounds(150, 50*6 + 20, 100, 50);
        colorLabel.setBounds(260, 50*6 + 20, 125, 50);

        drawBtn.setBounds(225,50*7 + 50,100,50);

        colorBtn.setFocusable(false);
        colorBtn.addActionListener(e -> setColor());

        drawBtn.setFocusable(false);
        drawBtn.addActionListener(actionEvent -> draw());
        colorLabel.setOpaque(true);
        colorLabel.setBackground(Color.BLACK);

        this.add(shapeNameLabel);
        this.add(nameField);
        this.add(xPosLabel);
        this.add(xPosField);
        this.add(yPosLabel);
        this.add(yPosField);
        this.add(xPosToLabel);
        this.add(xPosToField);
        this.add(yPosToLabel);
        this.add(yPosToField);
        this.add(colorLabel);
        this.add(colorBtn);
        this.add(drawBtn);
        this.getRootPane().setDefaultButton(drawBtn);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void setColor(){
        Color color = JColorChooser.showDialog(this, "Pick a color", Color.BLACK);
        colorLabel.setBackground(color);
        lineSegment.setColor(color);
    }

    public void draw() {
        int x, y;
        int xTo, yTo;

        try {
            x = Integer.parseInt(xPosField.getText());
            y = Integer.parseInt(yPosField.getText());
            xTo = Integer.parseInt(xPosToField.getText());
            yTo = Integer.parseInt(yPosToField.getText());

            if (nameField.getText().trim().equals(""))
                throw new NumberFormatException();

            engine.checkShapeName(nameField.getText());

            Map<String, String> p = new HashMap<>();
            p.put("name", nameField.getText());
            lineSegment.setProperties(p);
            lineSegment.setPosition(new Point(x, y));
            lineSegment.setEndPoint(new Point(xTo, yTo));
            engine.addShape(lineSegment);

            engine.refresh(engine.getGraphics());
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid data", "Failed!",
                                    JOptionPane.WARNING_MESSAGE);
        } catch (InvalidName e) {
            JOptionPane.showMessageDialog(null, "Name is already been used", "Invalid name!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

}


