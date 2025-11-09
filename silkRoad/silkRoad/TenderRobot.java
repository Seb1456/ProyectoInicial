package silkroad;

import java.awt.Color;

/**
 * Robot que solo toma la mitad del dinero de las tiendas.
 */
public class TenderRobot extends Robot {
    
    /**
     * Constructor del robot tender.
     * @param pos posición inicial del robot
     */
    public TenderRobot(int pos) {
        super("tender", pos, Color.GREEN);
    }
    
    /**
     * Recolecta solo la mitad de los fondos disponibles de la tienda.
     * @param store tienda de la cual recolectar
     * @return cantidad de dinero recolectado
     */
    @Override
    public int collectFrom(Store store) {
        if (store.canBeRobbedBy(this)) {
            int halfFunds = store.getFunds() / 2;
            return store.rob(halfFunds);
        }
        return 0;
    }
    
    /**
     * El robot tender puede moverse hacia atrás.
     * @return true
     */
    @Override
    public boolean canMoveBackward() {
        return true;
    }
}
