package shapes;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class Circle extends CustomShape {
    private int diameter;

    public Circle(int y, int x, int diameter, Color color) {
        super(y, x, color);
        this.diameter = diameter;
    }

    @Override
    protected void render() {
        if (!visible) return;
        Canvas.getInstance().render(this, color, new Ellipse2D.Double(y, x, diameter, diameter));
        Canvas.getInstance().pause(10);
    }
}

