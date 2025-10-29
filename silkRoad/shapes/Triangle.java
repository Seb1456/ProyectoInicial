package shapes;

import java.awt.Color;
import java.awt.Polygon;

public class Triangle extends CustomShape {
    private int width, height;
    private static final int VERTICES = 3;

    public Triangle(int y, int x, int width, int height, Color color) {
        super(y, x, color);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void render() {
        if (!visible) return;
        int[] xPoints = {x, x - width / 2, x + width / 2};
        int[] yPoints = {y, y + height, y + height};
        Canvas.getInstance().render(this, color, new Polygon(xPoints, yPoints, VERTICES));
        Canvas.getInstance().pause(10);
    }
}
