package silkroad;

import java.awt.Color;

/**
 * Tienda fighter que solo puede ser robada por robots con más dinero que ella.
 */
public class FighterStore extends Store {
    
    /**
     * Constructor de la tienda fighter.
     * @param money fondos de la tienda
     * @param pos posición de la tienda
     */
    public FighterStore(int money, int pos) {
        super("fighter", money, pos, Color.CYAN);
    }
    
    /**
     * Una tienda fighter solo puede ser robada por robots que tengan más fondos que ella.
     * @param robot robot que intenta robar
     * @return true si el robot puede robarla
     */
    @Override
    public boolean canBeRobbedBy(Robot robot) {
        return robot.getFunds() > this.getFunds();
    }
}
