package frontend.windows.shapeswindow;


import backend.drawableshapes.Circle;
import backend.exception.InvalidName;
import backend.Engine;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static backend.constants.Properties.*;

public class CircleWindow extends JDialog {

    private final Engine engine;
    private final JTextField nameField, xPosField, yPosField, radiusField;

    private final Circle circle;

    private final JLabel colorLabel, fillColorLabel;

    private final JCheckBox strokeEnableCheck, fillEnableCheck;

    JButton fillColorBtn, colorBtn;

    public CircleWindow(JFrame parent, Engine engine) {
        super(parent, "Circle probleties", Dialog.ModalityType.DOCUMENT_MODAL);
        this.engine = engine;
        circle = new Circle();

        this.setLayout(null);
        this.setSize(500, 525);

        JLabel shapeNameLabel = new JLabel("Shape name");
        nameField = new JTextField();

        JLabel xPosLabel = new JLabel("X position");
        xPosField = new JTextField();

        JLabel yPosLabel = new JLabel("Y position");
        yPosField = new JTextField();

        JLabel radiusLabel = new JLabel("Radius ");
        radiusField = new JTextField();

        colorLabel = new JLabel("  ");
        colorBtn = new JButton("Stroke");
        strokeEnableCheck = new JCheckBox("Stroke enable",true);
        strokeEnableCheck.addActionListener(e->strokeCheckChanged());

        fillColorLabel = new JLabel("  ");
        fillColorBtn = new JButton("Fill");
        fillEnableCheck = new JCheckBox("Fill enable", true);
        fillEnableCheck.addActionListener(e->fillCheckChanged());

        JButton drawBtn = new JButton("Draw");

        shapeNameLabel.setBounds(50, 50, 100, 50);
        nameField.setBounds(150, 50, 250, 50);

        xPosLabel.setBounds(50, 50*2, 100, 50);
        xPosField.setBounds(150, 50*2, 250, 50);

        yPosLabel.setBounds(50, 50*3, 100, 50);
        yPosField.setBounds(150, 50*3, 250, 50);

        radiusLabel.setBounds(50, 50*4, 100, 50);
        radiusField.setBounds(150, 50*4 , 250, 50);

        colorBtn.setBounds(100, 50*5 + 20, 100, 50);
        colorLabel.setBounds(200, 50*5 + 20, 125, 50);
        strokeEnableCheck.setBounds(350, 50*5+25, 150, 50);

        fillColorBtn.setBounds(100, 50*6 + 20, 100, 50);
        fillColorLabel.setBounds(200, 50*6 + 20, 125, 50);
        fillEnableCheck.setBounds(350, 50*6+25, 150, 50);

        drawBtn.setBounds(225,50*8,100,50);

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

        this.add(shapeNameLabel);
        this.add(nameField);
        this.add(xPosLabel);
        this.add(xPosField);
        this.add(yPosLabel);
        this.add(yPosField);
        this.add(radiusLabel);
        this.add(radiusField);
        this.add(colorLabel);
        this.add(colorBtn);
        this.add(fillColorLabel);
        this.add(fillColorBtn);
        this.add(strokeEnableCheck);
        this.add(fillEnableCheck);
        this.add(drawBtn);
        this.getRootPane().setDefaultButton(drawBtn);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void setColor(){
        Color color = JColorChooser.showDialog(this, "Pick a color", Color.BLACK);
        if (color == null) return;
        colorLabel.setBackground(color);
        circle.setColor(color);
    }

    public void setFillColor(){
        Color color = JColorChooser.showDialog(this, "Pick a color", Color.BLACK);
        if (color == null) return;
        fillColorLabel.setBackground(color);
        circle.setFillColor(color);
    }

    public void draw() {
        int x, y, radius;
        boolean stroke, fill;
        try {
            x = Integer.parseInt(xPosField.getText().trim());
            y = Integer.parseInt(yPosField.getText().trim());
            radius = Integer.parseInt(radiusField.getText().trim());
            stroke = strokeEnableCheck.isSelected();
            fill = fillEnableCheck.isSelected();

            if (!(fill || stroke)) {
                JOptionPane.showMessageDialog(null, "Requered At least one check ",
                        "Invalid data!",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (nameField.getText().trim().equals(""))
                throw new NumberFormatException();

            engine.checkShapeName(nameField.getText());

            Map<String, String> p = new HashMap<>();
            p.put(NAME_KEY, nameField.getText());
            p.put(SET_BORDER_KEY, Boolean.toString(stroke));
            p.put(SET_FILL_KEY, String.valueOf(fill));
            circle.setProperties(p);
            circle.setPosition(new Point(x, y));
            circle.setRadius(radius);
            engine.addShape(circle);
            engine.refresh(null);
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid data", "Failed!",
                    JOptionPane.WARNING_MESSAGE);
        } catch (InvalidName e) {
            JOptionPane.showMessageDialog(null, "Name is already been used",
                    "Invalid name!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void strokeCheckChanged() {
        colorBtn.setEnabled(strokeEnableCheck.isSelected());
    }
    private void fillCheckChanged() {
        fillColorBtn.setEnabled(fillEnableCheck.isSelected());
    }
}


