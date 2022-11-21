package backend;

import backend.constants.FileConfig;
import backend.exception.InvalidName;
import backend.struct.DrawingEngine;
import backend.struct.Pair;
import backend.struct.Shape;

import static backend.constants.FileConfig.VERSION;
import static backend.constants.Properties.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import static backend.constants.FileConfig.SERIAL_VERSION_UID;
import static frontend.MainFrame.DEF_TITLE_NAME;
import static frontend.MainFrame.frame;

public class Engine extends JPanel implements DrawingEngine, Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;
    private final String version;
    private String saveFile;
    private String saveFileName;
    private boolean flagRedoRest;
    private boolean fileSaved;
    private Vector<Shape> shapes;
    private Stack<Pair<String, Shape>> undoShapes;
    private Stack<Pair<String, Shape>> redoShapes;

    private final static int MAX_REDO = 10;
    private final static int MAX_UNDO = 10;
    private final static String CREATE_SHAPE = "Create";
    private final static String DELETE_SHAPE = "Delete";
    private final static String CHANGE_PROP = "Prop";
    private final static String RENAME_SHAPE = "Rename";

    public Engine() {
        super();
        version = FileConfig.VERSION;
        saveFile = "";
        saveFileName = DEF_TITLE_NAME;
        flagRedoRest = false;
        fileSaved = true;
        shapes = new Vector<>();
        undoShapes = new Stack<>();
        redoShapes = new Stack<>();
    }

    @Override
    public void addShape(Shape shape) {
        if (flagRedoRest) redoShapes.clear();
        addUndo(CREATE_SHAPE, shape.copy(shape));
        shapes.add(shape);
        flagRedoRest = true;
    }

    @Override
    public void removeShape(Shape shape) {
        if (flagRedoRest) redoShapes.clear();
        if (shape == null) return;
        addUndo(DELETE_SHAPE, shape.copy(shape));
        shapes.remove(shape);
        flagRedoRest = true;
    }

    @Override
    public Shape[] getShapes() {
        return shapes.toArray(new Shape[0]);
    }

    @Override
    public void refresh(Graphics canvas) {
        repaint();
        if (saveFileName.charAt(saveFileName.length()-1) != '*')
            saveFileName += "*";
        fileSaved = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape s : getShapes()) {
            s.draw(g);
        }
    }

    public String getDrawingTitle() {
        return saveFileName;
    }

    public Boolean isSaved() {
        return fileSaved;
    }

    public void changeColor (Shape shape, Color color, Color fillColor, boolean isBorder, boolean isFill) {
        if (flagRedoRest) redoShapes.clear();
        addUndo(CHANGE_PROP, shape.copy(shape));
        if (color != null) shape.setColor(color);
        if (fillColor != null) shape.setFillColor(fillColor);
        shape.addProperties(SET_BORDER_KEY, String.valueOf(isBorder));
        shape.addProperties(SET_FILL_KEY, String.valueOf(isFill));
        flagRedoRest = true;
    }

    public void renameShape(Shape shape, String name) {
        if (flagRedoRest) redoShapes.clear();
        shape.addProperties(PREV_NAME_KEY, shape.getProperties().get(NAME_KEY));
        addUndo(RENAME_SHAPE, shape.copy(shape));
        shape.addProperties(NAME_KEY, name);
        flagRedoRest = true;
    }

    private void addUndo(String key, Shape shape) {
        if (undoShapes.size() > MAX_UNDO)
            undoShapes.remove(MAX_UNDO);
        undoShapes.push(new Pair<>(key, shape));
    }

    private void addRedo(Pair<String, Shape> p) {
        if (redoShapes.size() > MAX_REDO)
            redoShapes.remove(MAX_REDO);
        redoShapes.push(p);
    }

    public Shape getShape(int index) {
        return shapes.get(index);
    }
    public Shape getShape(Shape shape) {
        for (Shape s : shapes) {
            if (shape.getProperties().get(NAME_KEY).equals(s.getProperties().get(NAME_KEY)))
                return s;
        }
        return null;

    }
    public Shape getShapeByPrevName(String name) {
        for (Shape s : shapes) {
            if (name.equals(s.getProperties().get(PREV_NAME_KEY))) {
                return s;
            }
        }
        return null;
    }



    public void refreshComboBox(JComboBox comboBox) {
        if (comboBox == null) return;

        comboBox.removeAllItems();
        Shape[] shapes = getShapes();
        comboBox.addItem("Select Shape");
        for (Shape shape : shapes) {
            comboBox.addItem(shape.getProperties().get("name"));
        }
    }

    public void checkShapeName(String shapeName) throws InvalidName {
        for (Shape s : getShapes()) {
            if (s.getProperties().get("name").equals(shapeName)) {
                throw new InvalidName();
            }
        }
    }

    public void undoChanges() {
        if (undoShapes.isEmpty() || undoShapes.peek() == null) {
            JOptionPane.showMessageDialog(null, "There is no more undo!", "Message",
                                            JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        operate(undoShapes.peek().key(), undoShapes.peek().value());
        addRedo(undoShapes.pop());
        undoShapes.pop();
        this.refresh(null);
        if (saveFileName.charAt(saveFileName.length() - 1) != '*')
            saveFileName = saveFileName + "*";
        if (frame != null) frame.setTitle(saveFileName);
    }

    public void redoChanges() {
        if (redoShapes.isEmpty() || redoShapes.peek() == null) {
            JOptionPane.showMessageDialog(null, "There is no more redo!", "Message",
                                            JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        operate(redoShapes.peek().key(), redoShapes.peek().value());
        redoShapes.pop();
        this.refresh(null);

        if (saveFileName.charAt(saveFileName.length() - 1) != '*')
            saveFileName = saveFileName + "*";
        if (frame != null) frame.setTitle(saveFileName);
    }

    private void operate (String key, Shape shape) {
        flagRedoRest = false;
        switch (key) {
            case DELETE_SHAPE:
                if (shape != null)
                    addShape(shape);
                break;
            case CREATE_SHAPE:
                if (shape != null)
                    removeShape(getShape(shape));
                break;
            case RENAME_SHAPE:
                if (shape != null){
                    Shape prevShape = getShapeByPrevName(shape.getProperties().get(PREV_NAME_KEY));
                    renameShape(prevShape, shape.getProperties().get(NAME_KEY));
                }
                break;
            case CHANGE_PROP:
                if (shape != null) {
                    Shape prevShape = getShape(shape);
                    changeColor(prevShape, shape.getColor(), shape.getFillColor(),
                            Boolean.parseBoolean(shape.getProperties().get(SET_BORDER_KEY)),
                            Boolean.parseBoolean(shape.getProperties().get(SET_FILL_KEY)));
                }
                break;
        }
    }

    public Boolean saveToFile(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();;
        if (saveFile.equals("")) {

            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) return true;
                    return f.getName().toLowerCase().endsWith(".ser");
                }

                @Override
                public String getDescription() {
                    return "Ser file (*.ser)";
                }
            });

            fileChooser.setAcceptAllFileFilterUsed(false);

            if (fileChooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
                return false;
            }
            saveFile = fileChooser.getSelectedFile().getPath() + ".ser";
        }


        try {
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            saveFileName = fileChooser.getSelectedFile().getName().split("\\.")[0];
            this.refresh(null);
            saveFileName = saveFileName.substring(0, saveFileName.length() - 1);
            if (frame != null) frame.setTitle(saveFileName);
            fileSaved = true;
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            saveFile = "";
            JOptionPane.showMessageDialog(frame, "Can't save the file!", "Something went wrong!",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    public void openFromFile(JFrame frame) {
        String openFile;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                return f.getName().toLowerCase().endsWith(".ser");
            }

            @Override
            public String getDescription() {
                return "Ser file (*.ser)";
            }
        });

        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showDialog(frame, "Open") != JFileChooser.APPROVE_OPTION) {
            return;
        }

        openFile = fileChooser.getSelectedFile().getPath();

        try {
            Engine newEngine;
            FileInputStream fileIn = new FileInputStream(openFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            newEngine = (Engine) in.readObject();
            in.close();
            fileIn.close();

            if (newEngine.version == null || !newEngine.version.equals(VERSION)) {
                JOptionPane.showMessageDialog(frame, "Unmatched Version!", "Failed!",
                                            JOptionPane.WARNING_MESSAGE);
                return;
            }

            openFile = fileChooser.getSelectedFile().getPath();
            saveFileName = fileChooser.getSelectedFile().getName().split("\\.")[0];

            this.copy(newEngine);

            saveFile = openFile;
            if (frame != null) frame.setTitle(saveFileName);
            this.refresh(null);
            saveFileName = saveFileName.substring(0, saveFileName.length() - 1);
            fileSaved = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());

            JOptionPane.showMessageDialog(frame, "Can't open the file!", "Something went wrong!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void newDrawing(JFrame frame) {
        if (isSaved()) {
            this.copy(new Engine());
            saveFileName = DEF_TITLE_NAME;
            if (frame != null) frame.setTitle(DEF_TITLE_NAME);
            refresh(null);
            saveFileName = saveFileName.substring(0, saveFileName.length() - 1);
            fileSaved = true;
            return;
        }

        String[] options = {"Save and create", "Don't sava and create", "Cancel"};
        int response = JOptionPane.showOptionDialog(frame, "Do you to save?", "New Drawing",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if ((response == JOptionPane.YES_OPTION && this.saveToFile(frame)) ||
                response == JOptionPane.NO_OPTION) {
            this.copy(new Engine());
            saveFileName = DEF_TITLE_NAME;
            if (frame != null) frame.setTitle(DEF_TITLE_NAME);
            this.refresh(null);
            fileSaved = true;
        }
    }

    public void exportImage(JFrame frame) {
        String path;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                return f.getName().toLowerCase().endsWith(".jpg");
            }

            @Override
            public String getDescription() {
                return "JPG file (*.jpg)";
            }
        });

        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showDialog(frame, "Export Drawing") == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getPath();
        } else {
            return;
        }

        BufferedImage image = new BufferedImage(this.getWidth(),this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        this.paint(g2);

        try{
            ImageIO.write(image, "jpg", new File(path + ".jpg"));
            fileSaved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copy(Engine e) {
        this.redoShapes = e.redoShapes;
        this.undoShapes = e.undoShapes;
        this.shapes = e.shapes;
    }

}