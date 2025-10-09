import java.awt.Color;

/**
 * Representa un segmento de la carretera.
 */
public class Road {
    private static final int SIZE = 100;
    private final Rectangle segment;
    
    /**
     * Constructor del segmento de carretera.
     * @param y coordenada Y
     * @param x coordenada X
     */
    public Road(int y, int x) {
        this.segment = new Rectangle(y, x, SIZE, SIZE, Color.GRAY);
        this.segment.show(true);
    }
}

