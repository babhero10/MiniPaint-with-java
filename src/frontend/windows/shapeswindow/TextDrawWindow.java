package frontend.windows.shapeswindow;


import backend.Engine;
import backend.drawableshapes.TextShape;
import backend.exception.InvalidName;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static backend.constants.Properties.*;

public class TextDrawWindow extends JDialog {

    private final Engine engine;
    private final JTextField nameField, xPosField, yPosField, textField, fontSizeField;

    private final TextShape textShape;

    JLabel colorLabel;
    JButton colorBtn;

    public TextDrawWindow(JFrame parent, Engine engine) {
        super(parent, "Text probleties", ModalityType.DOCUMENT_MODAL);
        this.engine = engine;
        textShape = new TextShape();

        this.setLayout(null);
        this.setSize(500, 580);

        JLabel shapeNameLabel = new JLabel("Shape name");
        nameField = new JTextField();

        JLabel xPosLabel = new JLabel("X position");
        xPosField = new JTextField();

        JLabel yPosLabel = new JLabel("Y position");
        yPosField = new JTextField();

        JLabel textLabel = new JLabel("Text ");
        textField = new JTextField();

        JLabel fontSizeLabel = new JLabel("Font size ");
        fontSizeField = new JTextField();

        colorLabel = new JLabel("  ");
        colorBtn = new JButton("Stroke");

        colorLabel = new JLabel("  ");
        colorBtn = new JButton("Stroke");

        JButton drawBtn = new JButton("Draw");

        shapeNameLabel.setBounds(50, 50, 100, 50);
        nameField.setBounds(150, 50, 250, 50);

        xPosLabel.setBounds(50, 50*2, 100, 50);
        xPosField.setBounds(150, 50*2, 250, 50);

        yPosLabel.setBounds(50, 50*3, 100, 50);
        yPosField.setBounds(150, 50*3, 250, 50);

        textLabel.setBounds(50, 50*4, 100, 50);
        textField.setBounds(150, 50*4 , 250, 50);

        fontSizeLabel.setBounds(50, 50*5, 100, 50);
        fontSizeField.setBounds(150, 50*5 , 250, 50);

        colorBtn.setBounds(150, 50*6 + 20, 100, 50);
        colorLabel.setBounds(260, 50*6 + 20, 125, 50);

        drawBtn.setBounds(225,50*8 + 50,100,50);

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
        this.add(textLabel);
        this.add(textField);
        this.add(fontSizeLabel);
        this.add(fontSizeField);
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
        if (color == null) return;
        colorLabel.setBackground(color);
        textShape.setColor(color);
    }

    public void draw() {
        try {
            int x, y, textSize;
            String text;

            x = Integer.parseInt(xPosField.getText().trim());
            y = Integer.parseInt(yPosField.getText().trim());
            textSize = Integer.parseInt(fontSizeField.getText().trim());
            text = textField.getText();

            if (textField.getText().equals(""))
                throw new NumberFormatException();

            if (nameField.getText().trim().equals(""))
                throw new NumberFormatException();

            engine.checkShapeName(nameField.getText());

            Map<String, String> p = new HashMap<>();
            p.put(NAME_KEY, nameField.getText());
            textShape.setProperties(p);
            textShape.setPosition(new Point(x, y));
            textShape.setText(text);
            textShape.setTextSize(textSize);
            engine.addShape(textShape);

            engine.refresh(null);
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


