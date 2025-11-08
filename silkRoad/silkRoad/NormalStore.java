package silkRoad;

import java.awt.Color;

/**
 * Tienda normal que puede ser robada por cualquier robot.
 */
public class NormalStore extends Store {
    
    /**
     * Constructor de la tienda normal.
     * @param money fondos de la tienda
     * @param pos posición de la tienda
     */
    public NormalStore(int money, int pos) {
        super("normal", money, pos, Color.ORANGE);
    }
    
    /**
     * Una tienda normal puede ser robada por cualquier robot.
     * @param robot robot que intenta robar
     * @return true siempre
     */
    @Override
    public boolean canBeRobbedBy(Robot robot) {
        return true;
    }
}