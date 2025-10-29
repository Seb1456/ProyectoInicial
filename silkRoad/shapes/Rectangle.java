package shapes;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Rectangle extends CustomShape {
    private int width, height;

    public Rectangle(int y, int x, int width, int height, Color color) {
        super(y, x, color);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void render() {
        if (!visible) return;
        Canvas.getInstance().render(this, color, new Rectangle2D.Double(y, x, width, height));
        Canvas.getInstance().pause(10);
    }
}

