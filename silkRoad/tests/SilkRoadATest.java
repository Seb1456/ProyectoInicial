package tests;
import silkRoad.*;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SilkRoadATest {

    /**
     * Verifica que un robot normal recoja correctamente los fondos
     * de una tienda normal en la misma posición.
     */
    @Test
    public void testRobotCollectsStoreFunds() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeStore("normal", 3, 150);
        road.placeRobot("normal", 3);

        assertEquals(0, road.getFunds(3), "La tienda debería quedar sin fondos.");
        assertTrue(road.profit() >= 150, "La ganancia total debe incluir los fondos de la tienda vaciada.");
        assertTrue(road.hasRobotAt(3), "El robot debe estar presente en la posición 3.");
    }

    /**
     * Verifica que la simulación de un día mueva los robots y genere ganancias
     * cuando encuentran tiendas en el camino.
     */
    @Test
    public void testSimulateDayGeneratesProfit() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeRobot("normal", 2);
        road.placeStore("normal", 3, 120);
        road.placeRobot("neverback", 4);
        road.placeStore("fighter", 6, 200);

        road.simulateDay();

        int totalProfit = road.profit();
        assertTrue(totalProfit > 0, "Debe haberse generado ganancia durante la simulación.");
        assertTrue(road.getFunds(3) < 120 || road.getFunds(6) < 200, "Alguna tienda debe haber perdido fondos.");
    }
}
