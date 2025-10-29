package shapes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Canvas extends JFrame {
    private static Canvas instance;
    private static final int WINDOW_SIZE = 500;

    public static Canvas getInstance() {
        if (instance == null) {
            instance = new Canvas("SilkRoad Game", WINDOW_SIZE, WINDOW_SIZE, Color.YELLOW);
        }
        instance.setVisible(true);
        return instance;
    }

    private final JFrame frame;
    private final CanvasPanel panel;
    private Graphics2D graphics;
    private final Color background;
    private Image buffer;
    private final List<Object> shapes;
    private static JProgressBar progressBar;
    private final HashMap<Object, ShapeData> shapeData;

    private Canvas(String title, int width, int height, Color bg) {
        frame = new JFrame();
        panel = new CanvasPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        panel.setPreferredSize(new Dimension(width, height));
        background = bg;
        shapes = new ArrayList<>();
        shapeData = new HashMap<>();

        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        container.add(progressBar, BorderLayout.SOUTH);

        frame.setContentPane(container);
        frame.pack();
    }

    public void setVisible(boolean visible) {
        if (graphics == null) {
            Dimension size = panel.getSize();
            buffer = panel.createImage(size.width, size.height);
            graphics = (Graphics2D) buffer.getGraphics();
            graphics.setColor(background);
            graphics.fillRect(0, 0, size.width, size.height);
            graphics.setColor(Color.BLACK);
        }
        frame.setVisible(visible);
    }

    public void render(Object obj, Color color, Shape shape) {
        shapes.remove(obj);
        shapes.add(obj);
        shapeData.put(obj, new ShapeData(shape, color));
        update();
    }

    public void clear(Object obj) {
        shapes.remove(obj);
        shapeData.remove(obj);
        update();
    }

    public void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

    private void update() {
        clearCanvas();
        for (Object obj : shapes) {
            ShapeData data = shapeData.get(obj);
            if (data != null) {
                data.render(graphics);
            }
        }
        panel.repaint();
    }

    private void clearCanvas() {
        Color current = graphics.getColor();
        graphics.setColor(background);
        Dimension size = panel.getSize();
        graphics.fillRect(0, 0, size.width, size.height);
        graphics.setColor(current);
    }

    private class CanvasPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(buffer, 0, 0, null);
        }
    }

    private static class ShapeData {
        private final Shape shape;
        private final Color color;

        ShapeData(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        void render(Graphics2D g) {
            g.setColor(color);
            g.fill(shape);
            g.draw(shape);
        }
    }

    public static void setProgress(int value) {
        progressBar.setValue(value);
    }
}
