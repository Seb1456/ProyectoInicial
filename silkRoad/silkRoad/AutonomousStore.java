package silkroad;

import java.awt.Color;
import java.util.Random;

/**
 * Tienda autónoma que escoge su propia posición al ser creada.
 */
public class AutonomousStore extends Store {
    private static Random random = new Random();
    private static final int MAX_POSITIONS = 17;
    
    /**
     * Constructor de la tienda autónoma.
     * Escoge una posición aleatoria ignorando la posición sugerida.
     * @param money fondos de la tienda
     * @param suggestedPos posición sugerida (será ignorada)
     */
    public AutonomousStore(int money, int suggestedPos) {
        super("autonomous", money, chooseRandomPosition(), Color.MAGENTA);
    }
    
    /**
     * Escoge una posición aleatoria válida.
     * @return posición aleatoria entre 0 y 16
     */
    private static int chooseRandomPosition() {
        return random.nextInt(MAX_POSITIONS);
    }
    
    /**
     * Una tienda autónoma puede ser robada por cualquier robot.
     * @param robot robot que intenta robar
     * @return true siempre
     */
    @Override
    public boolean canBeRobbedBy(Robot robot) {
        return true;
    }
}
