package shapes;

import java.awt.Color;

public abstract class CustomShape {
    protected int x, y;
    protected Color color;
    protected boolean visible;

    public CustomShape(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
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
    
    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        if (visible) {
            render();
        }
    }


    public void setColor(Color color) {
        this.color = color;
        if (visible) render();
    }

    protected abstract void render();

    protected void clear() {
        Canvas.getInstance().clear(this);
    }
}
