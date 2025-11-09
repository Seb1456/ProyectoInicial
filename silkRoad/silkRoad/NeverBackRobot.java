package silkroad;

import java.awt.Color;

/**
 * Robot que nunca se devuelve, solo avanza.
 */
public class NeverBackRobot extends Robot {
    
    /**
     * Constructor del robot neverback.
     * @param pos posición inicial del robot
     */
    public NeverBackRobot(int pos) {
        super("neverback", pos, Color.RED);
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
     * El robot neverback no puede moverse hacia atrás.
     * @return false
     */
    @Override
    public boolean canMoveBackward() {
        return false;
    }
}
