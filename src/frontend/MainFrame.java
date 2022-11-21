package frontend;

import backend.Engine;
import backend.drawableshapes.LineSegment;
import frontend.windows.customizing.RenameWindow;
import frontend.windows.shapeswindow.CircleWindow;
import frontend.windows.shapeswindow.LineSegmentWindow;
import frontend.windows.shapeswindow.RectangleWindow;
import frontend.windows.shapeswindow.SquareWindow;
import frontend.windows.customizing.ChangeColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


// 1150 638

public class MainFrame {
    public static final String DEF_TITLE_NAME = "Untitled";

    private JPanel main_panel;
    private JComboBox shapesComb;
    private JButton colorizeBtn;
    private JButton deleteBtn;
    private JButton circleBtn;
    private JButton lineSegmentBtn;
    private JButton squareBtn;
    private JButton rectangleBtn;
    public JPanel drawingPanel;
    private JButton renameBtn;
    public static JFrame frame;

    public MainFrame() {

        shapesComb.setFocusable(false);
        circleBtn.setFocusable(false);
        rectangleBtn.setFocusable(false);
        squareBtn.setFocusable(false);
        lineSegmentBtn.setFocusable(false);
        colorizeBtn.setFocusable(false);
        deleteBtn.setFocusable(false);
        renameBtn.setFocusable(false);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        JMenuItem redoItem = new JMenuItem("Redo");
        JMenuItem undoItem = new JMenuItem("Undo");

        JMenuItem newItem = new JMenuItem("New Drawing");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exportItem = new JMenuItem("Export as image");
        JMenuItem exitItem = new JMenuItem("Exit");

        redoItem.addActionListener(e -> redoItemEvent());
        undoItem.addActionListener(e -> undoItemEvent());

        newItem.addActionListener(e -> newItemEvent());
        openItem.addActionListener(e -> openItemEvent());
        saveItem.addActionListener(e -> ((Engine)drawingPanel).saveToFile(frame));
        exportItem.addActionListener(e -> ((Engine)drawingPanel).exportImage(frame));
        exitItem.addActionListener(e -> savingDialog());

        undoItem.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        redoItem.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        newItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        openItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        exportItem.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exportItem);
        fileMenu.add(exitItem);

        editMenu.add(redoItem);
        editMenu.add(undoItem);

        frame.setJMenuBar(menuBar);
        circleBtn.addActionListener(e -> drawCircle());
        lineSegmentBtn.addActionListener(e -> drawLineSegment());
        rectangleBtn.addActionListener(e -> drawRectangle());
        squareBtn.addActionListener(e -> drawSquare());

        deleteBtn.addActionListener(e -> deleteShape());
        colorizeBtn.addActionListener(e -> changeColor());
        renameBtn.addActionListener(e -> renameShape());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                savingDialog();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);

                frame.setTitle(((Engine)drawingPanel).getDrawingTitle());
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            System.out.println("Can't set GTK+");
        }

        frame = new JFrame(DEF_TITLE_NAME);
        frame.setContentPane(new MainFrame().main_panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void drawCircle() {
        new CircleWindow(frame, (Engine) drawingPanel);
        ((Engine) drawingPanel).refreshComboBox(shapesComb);
    }

    private void drawLineSegment() {
        new LineSegmentWindow(frame, (Engine) drawingPanel);
        ((Engine) drawingPanel).refreshComboBox(shapesComb);
    }

    private void drawRectangle() {
        new RectangleWindow(frame, (Engine) drawingPanel);
        ((Engine) drawingPanel).refreshComboBox(shapesComb);
    }

    private void drawSquare() {
        new SquareWindow(frame, (Engine) drawingPanel);
        ((Engine) drawingPanel).refreshComboBox(shapesComb);
    }

    private boolean uncheckSelectedItem() {
        if (shapesComb.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(frame, "Select a Shape!", "Invalid Shape!",
                    JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private void deleteShape() {
        if (uncheckSelectedItem()) {
            return;
        }

        Engine e = (Engine) drawingPanel;
        e.removeShape(e.getShapes()[shapesComb.getSelectedIndex() - 1]);
        e.refresh(null);
        e.refreshComboBox(shapesComb);
    }

    private void changeColor() {
        if (uncheckSelectedItem()) {
            return;
        }


        Engine e = (Engine) drawingPanel;
        int index = shapesComb.getSelectedIndex() - 1;
        Color[] c = new ChangeColor(frame, e.getShape(index) instanceof LineSegment).
                showDialog(e.getShape(index).getColor(), e.getShape(index).getFillColor());

        if (c[0] == null && c[1] == null) return;
        e.changeColor(e.getShape(index), c[0], c[1], c[0] != null, c[1] != null);
        e.refresh(null);
    }

    private void renameShape() {
        if (uncheckSelectedItem()) {
            return;
        }

        Engine e = (Engine) drawingPanel;
        String name = new RenameWindow(frame).showDialog(e);
        if (name == null) return;

        e.renameShape(e.getShapes()[shapesComb.getSelectedIndex() - 1], name);
        e.refresh(null);
        e.refreshComboBox(shapesComb);
    }

    private void undoItemEvent() {
        ((Engine)drawingPanel).undoChanges();
        ((Engine)drawingPanel).refreshComboBox(shapesComb);
    }

    private void redoItemEvent() {
        ((Engine)drawingPanel).redoChanges();
        ((Engine)drawingPanel).refreshComboBox(shapesComb);
    }

    private void openItemEvent() {
        ((Engine)drawingPanel).openFromFile(frame);
        ((Engine)drawingPanel).refreshComboBox(shapesComb);
    }

    private void newItemEvent() {
        ((Engine)drawingPanel).newDrawing(frame);
        ((Engine)drawingPanel).refreshComboBox(shapesComb);
    }

    private void savingDialog() {
        if (((Engine)drawingPanel).isSaved()) {
            frame.dispose();
            return;
        }

        String[] options = {"Save and close", "Don't sava and close", "Cancel"};
        int response = JOptionPane.showOptionDialog(frame, "Do you want to save?", "Exit",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (response == JOptionPane.YES_OPTION) {
            if (((Engine)drawingPanel).saveToFile(frame)) {
                frame.dispose();
            }
        } else if (response == JOptionPane.NO_OPTION) {
            frame.dispose();
        }
    }


    public void createUIComponents() {
        this.drawingPanel = new Engine();
    }
}