package frontend;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import backend.drawableshapes.Rectangle;
import backend.errors.InvalidName;

public class SquareWindow extends JDialog {

    private final Engine engine;
    private final JTextField nameField, xPosField, yPosField, lengthFiled;

    private final Rectangle square;

    private final JLabel colorLabel, fillColorLabel;

    public SquareWindow(JFrame parent, Engine engine) {
        super(parent, "Square probleties", ModalityType.DOCUMENT_MODAL);
        this.engine = engine;
        square = new Rectangle();

        this.setLayout(null);
        this.setSize(500, 530);

        JLabel shapeNameLabel = new JLabel("Shape name");
        nameField = new JTextField();

        JLabel xPosLabel = new JLabel("X position");
        xPosField = new JTextField();

        JLabel yPosLabel = new JLabel("Y position");
        yPosField = new JTextField();

        JLabel lengthLabel = new JLabel("Length ");
        lengthFiled = new JTextField();

        colorLabel = new JLabel("  ");
        JButton colorBtn = new JButton("Stroke");

        fillColorLabel = new JLabel("  ");
        JButton fillColorBtn = new JButton("Fill");

        JButton drawBtn = new JButton("Draw");

        shapeNameLabel.setBounds(50, 50, 100, 50);
        nameField.setBounds(150, 50, 250, 50);

        xPosLabel.setBounds(50, 50*2, 100, 50);
        xPosField.setBounds(150, 50*2, 250, 50);

        yPosLabel.setBounds(50, 50*3, 100, 50);
        yPosField.setBounds(150, 50*3, 250, 50);

        lengthLabel.setBounds(50, 50*4, 100, 50);
        lengthFiled.setBounds(150, 50*4 , 250, 50);

        colorBtn.setBounds(150, 50*5 + 20, 100, 50);
        colorLabel.setBounds(260, 50*5 + 20, 125, 50);

        fillColorBtn.setBounds(150, 50*6 + 20, 100, 50);
        fillColorLabel.setBounds(260, 50*6 + 20, 125, 50);

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
        this.add(lengthLabel);
        this.add(lengthFiled);
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
        square.setColor(color);
    }

    public void setFillColor(){
        Color color = JColorChooser.showDialog(this, "Pick a color", Color.BLACK);
        fillColorLabel.setBackground(color);
        square.setFillColor(color);
    }

    public void draw() {
        int x, y;
        int length;

        try {
            x = Integer.parseInt(xPosField.getText());
            y = Integer.parseInt(yPosField.getText());
            length = Integer.parseInt(lengthFiled.getText());

            if (nameField.getText().trim().equals(""))
                throw new NumberFormatException();

            engine.checkShapeName(nameField.getText());

            Map<String, String> p = new HashMap<>();
            p.put("name", nameField.getText());
            square.setProperties(p);
            square.setPosition(new Point(x, y));
            square.setHeight(length);
            square.setWidth(length);
            engine.addShape(square);

            engine.refresh(engine.getGraphics());
            this.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid data", "Failed!", JOptionPane.WARNING_MESSAGE);
        } catch (InvalidName e) {
            JOptionPane.showMessageDialog(null, "Name is already been used", "Invalid name!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

}


