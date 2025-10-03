import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Rectangle {
    private int xPos, yPos, width, height;
    private Color color;
    private boolean visible;

    public Rectangle(int y, int x, int h, int w, Color col) {
        this.yPos = y;
        this.xPos = x;
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
        canvas.render(this, color, new Rectangle2D.Double(xPos, yPos, width, height));
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