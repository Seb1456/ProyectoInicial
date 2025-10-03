import java.awt.Color;
import java.awt.Polygon;

public class Triangle {
    private static final int VERTICES = 3;
    private int xBase, yBase, width, height;
    private Color color;
    private boolean visible;

    public Triangle(int y, int x, int h, int w, Color col) {
        this.yBase = y;
        this.xBase = x;
        this.height = h;
        this.width = w;
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
        int[] xPoints = {xBase, xBase + (width / 2), xBase - (width / 2)};
        int[] yPoints = {yBase, yBase + height, yBase + height};
        canvas.render(this, color, new Polygon(xPoints, yPoints, VERTICES));
        canvas.pause(10);
    }

    private void clear() {
        if (!visible) return;
        Canvas canvas = Canvas.getInstance();
        canvas.clear(this);
    }
    
    public void setColor(Color col) {
        this.color = col;
        if (visible) {
            render();
        }
    }
}