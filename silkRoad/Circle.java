import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Circle {
    private final int diameter;
    private int xPos, yPos;
    private final Color color;
    private boolean visible;

    public Circle(int y, int x, int size, Color col) {
        this.yPos = y;
        this.xPos = x;
        this.diameter = size;
        this.color = col;
        this.visible = false;
    }

    public void show(boolean visible) {
        this.visible = visible;
        if (visible) {
            render();
        } else {
            clear();
        }
    }

    private void render() {
        if (!visible) return;
        Canvas canvas = Canvas.getInstance();
        canvas.render(this, color, new Ellipse2D.Double(xPos, yPos, diameter, diameter));
        canvas.pause(10);
    }

    private void clear() {
        Canvas canvas = Canvas.getInstance();
        canvas.clear(this);
    }


    public void moveTo(int newY, int newX) {
        clear();
        this.yPos = newY;
        this.xPos = newX;
        render();
    }
}