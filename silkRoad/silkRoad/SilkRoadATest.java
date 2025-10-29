package silkRoad;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
 
public class SilkRoadATest {
 
    /**
     * Prueba de aceptación: ejecución completa con múltiples robots y tiendas.
     */
    @Test
    public void testFullMarathonSimulation() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeRobot("normal", 2);
        road.placeRobot("neverback", 4);
        road.placeRobot("tender", 6);
        road.placeRobot("thief", 8);
        road.placeStore("normal", 3, 120);
        road.placeStore("fighter", 5, 200);
        road.placeStore("autonomous", 7, 150);
        road.placeStore("normal", 9, 80);
        road.simulateDay();
        int total = road.profit();
        assertTrue(total > 0);
        assertTrue(road.getStoreFunds(3) < 120);
        assertTrue(road.getStoreFunds(9) < 80);
    }
 
    /**
     * Prueba de aceptación: simulación prolongada con reinicio de día.
     */
    @Test
    public void testExtendedSimulationWithReboot() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeRobot("neverback", 4);
        road.placeRobot("normal", 6);
        road.placeStore("fighter", 5, 200);
        road.placeStore("normal", 4, 100);
        road.simulateDay();
        int profitBefore = road.profit();
        road.reboot();
        int profitAfter = road.profit();
        assertTrue(profitAfter >= profitBefore);
        assertTrue(road.hasRobotAt(4));
        assertFalse(road.hasRobotAt(6));
    }
}