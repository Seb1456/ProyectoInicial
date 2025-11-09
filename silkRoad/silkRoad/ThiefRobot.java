package silkroad;

import java.awt.Color;

/**
 * Robot ladrón que solo roba 1/3 de los fondos de las tiendas.
 * Respeta las restricciones de las tiendas fighter (necesita tener más fondos).
 */
public class ThiefRobot extends Robot {
    
    /**
     * Constructor del robot thief.
     * @param pos posición inicial del robot
     */
    public ThiefRobot(int pos) {
        super("thief", pos, Color.PINK);
    }
    
    /**
     * Recolecta 1/3 de los fondos de la tienda.
     * Para tiendas fighter, solo puede robar si tiene más fondos que ella.
     * @param store tienda de la cual recolectar
     * @return cantidad de dinero recolectado
     */
    @Override
    public int collectFrom(Store store) {
        if (store.canBeRobbedBy(this)) {
            int oneThird = store.getFunds() / 3;
            return store.rob(oneThird);
        }
        return 0;
    }
    
    /**
     * El robot thief puede moverse hacia atrás.
     * @return true
     */
    @Override
    public boolean canMoveBackward() {
        return true;
    }
}
