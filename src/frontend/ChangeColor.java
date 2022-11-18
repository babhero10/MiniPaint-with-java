package frontend;

import javax.swing.*;
import java.awt.*;


public class ChangeColor extends JDialog {

    private Color shapeFillColor, shapeColorStroke;

    private JLabel colorLabel, fillColorLabel;

    public ChangeColor(JFrame parent) {
        super(parent, "Circle probleties", ModalityType.DOCUMENT_MODAL);
        this.shapeFillColor = null;
        this.shapeColorStroke = null;
    }

    public Color[] showDialog(Color stroke, Color fill) {
        this.setLayout(null);
        this.setSize(500, 325);

        colorLabel = new JLabel("  ");
        JButton colorBtn = new JButton("Stroke");

        fillColorLabel = new JLabel("  ");
        JButton fillColorBtn = new JButton("Fill");

        JButton change = new JButton("Change");

        colorBtn.setBounds(150, 50 + 20, 100, 50);
        colorLabel.setBounds(260, 50 + 20, 125, 50);
        fillColorBtn.setBounds(150, 50*2 + 20, 100, 50);
        fillColorLabel.setBounds(260, 50*2 + 20, 125, 50);

        change.setBounds(225,50*4,100,50);

        colorBtn.setFocusable(false);
        colorBtn.addActionListener(e -> setColor());
        fillColorBtn.setFocusable(false);
        fillColorBtn.addActionListener(e -> setFillColor());

        change.setFocusable(false);
        change.addActionListener(actionEvent -> change());
        colorLabel.setOpaque(true);
        colorLabel.setBackground(stroke);

        fillColorLabel.setOpaque(true);
        fillColorLabel.setBackground(fill);

        this.add(colorLabel);
        this.add(colorBtn);
        this.add(fillColorLabel);
        this.add(fillColorBtn);
        this.add(change);
        this.getRootPane().setDefaultButton(change);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        return new Color[]{shapeColorStroke, shapeFillColor};
    }

    public void setColor(){
        Color color = JColorChooser.showDialog(this, "Pick a color", Color.BLACK);
        colorLabel.setBackground(color);
    }

    public void setFillColor(){
        Color color = JColorChooser.showDialog(this, "Pick a color", Color.BLACK);
        fillColorLabel.setBackground(color);
    }

    public void change() {
        this.shapeFillColor = fillColorLabel.getBackground();
        this.shapeColorStroke = colorLabel.getBackground();
        dispose();
    }

}


