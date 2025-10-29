package silkRoad;

import java.awt.Color;
import shapes.*;

public class Road {
    private static final int SIZE = 100;
    private final Rectangle segment;
    
    public Road(int y, int x) {
        this.segment = new Rectangle(y, x, SIZE, SIZE, Color.GRAY);
        this.segment.show(true);
    }
}

