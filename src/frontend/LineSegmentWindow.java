package frontend;


import backend.drawableshapes.LineSegment;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LineSegmentWindow extends JDialog {

    private final Engine engine;
    private final JTextField xPosField, yPosField, xPosToField, yPosToField;

    private final LineSegment lineSegment;

    private final JLabel colorLabel;

    public LineSegmentWindow(JFrame parent, Engine engine) {
        super(parent, "Line probleties", ModalityType.DOCUMENT_MODAL);
        this.engine = engine;
        lineSegment = new LineSegment();

        this.setLayout(null);
        this.setSize(500, 450);

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

        xPosLabel.setBounds(50, 50, 100, 50);
        xPosField.setBounds(150, 50, 250, 50);

        yPosLabel.setBounds(50, 50*2, 100, 50);
        yPosField.setBounds(150, 50*2, 250, 50);

        xPosToLabel.setBounds(50, 50*3, 100, 50);
        xPosToField.setBounds(150, 50*3 , 250, 50);

        yPosToLabel.setBounds(50, 50*4, 100, 50);
        yPosToField.setBounds(150, 50*4 , 250, 50);

        colorBtn.setBounds(150, 50*5 + 20, 100, 50);
        colorLabel.setBounds(260, 50*5 + 20, 125, 50);

        drawBtn.setBounds(225,50*6 + 50,100,50);

        colorBtn.setFocusable(false);
        colorBtn.addActionListener(e -> setColor());

        drawBtn.setFocusable(false);
        drawBtn.addActionListener(actionEvent -> draw());
        colorLabel.setOpaque(true);
        colorLabel.setBackground(Color.BLACK);

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
        double xTo, yTo;

        try {
            x = Integer.parseInt(xPosField.getText());
            y = Integer.parseInt(yPosField.getText());
            xTo = Double.parseDouble(xPosToField.getText());
            yTo = Double.parseDouble(yPosToField.getText());

            Map<String, Double> p = new HashMap<>();
            p.put("xTo", xTo);
            p.put("yTo", yTo);
            lineSegment.setProperties(p);
            lineSegment.setPosition(new Point(x, y));
            engine.addShape(lineSegment);

            this.dispose();
            engine.refresh(engine.getGraphics());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid data", "Failed!",
                                    JOptionPane.WARNING_MESSAGE);
        }
    }

}


