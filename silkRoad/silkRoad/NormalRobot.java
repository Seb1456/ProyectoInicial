package silkRoad;

import java.awt.Color;

/**
 * Robot normal que recolecta todos los fondos de las tiendas que visita.
 */
public class NormalRobot extends Robot {
    
    /**
     * Constructor del robot normal.
     * @param pos posición inicial del robot
     */
    public NormalRobot(int pos) {
        super("normal", pos, Color.BLUE);
    }
    
    /**
     * Recolecta todos los fondos disponibles de la tienda.
     * @param store tienda de la cual recolectar
     * @return cantidad de dinero recolectado
     */
    @Override
    public int collectFrom(Store store) {
        if (store.canBeRobbedBy(this)) {
            return store.rob(store.getFunds());
        }
        return 0;
    }
    
    /**
     * El robot normal puede moverse hacia atrás.
     * @return true
     */
    @Override
    public boolean canMoveBackward() {
        return true;
    }
}