package tests;
import silkRoad.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
 

public class SilkRoadC4Test {
 
    /**
     * Verifica que un robot normal al colocarse en la misma posición que una tienda normal
     * obtenga la totalidad de los fondos y que la tienda quede vacía.
     */
    @Test
    public void testNormalRobotAndStore() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeStore("normal", 2, 90);
        road.placeRobot("normal", 2);
 
        assertEquals(90, road.profit(), "El robot normal debe obtener todos los fondos de la tienda normal");
        assertFalse(road.stores[2].isInStock(), "La tienda debe quedar vacía");
    }
 
    /**
     * Verifica que los robots del tipo neverback permanezcan en el camino tras ejecutar reboot(),
     * mientras que los demás sean retirados.
     */
    @Test
    public void testNeverbackRobotDoesNotReturnOnReboot() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeStore("normal", 3, 100);
        road.placeRobot("neverback", 3);
        assertEquals(100, road.profit(), "neverback debe ganar el 100% de la tienda");
 
        road.reboot();
        assertTrue(road.getAllRobots().stream().anyMatch(r -> "neverback".equals(r.getType())), 
            "El robot neverback debe permanecer tras reboot");
    }
 
    /**
     * Verifica que el robot del tipo tender solo obtenga la mitad de los fondos disponibles
     * y que la tienda permanezca parcialmente abastecida.
     */
    @Test
    public void testTenderRobotHalvesFunds() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeStore("normal", 5, 80);
        road.placeRobot("tender", 5);
 
        assertEquals(40, road.profit(), "tender debe ganar la mitad de los fondos");
        assertTrue(road.stores[5].isInStock(), "La tienda no debe vaciarse totalmente");
    }
 
    /**
     * Verifica que el robot del tipo thief robe un tercio de los fondos disponibles,
     * y que los fondos de la tienda se reduzcan tras la interacción.
     */
    @Test
    public void testThiefRobotStealsOneThird() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeStore("normal", 6, 90);
        road.placeRobot("thief", 6);
 
        assertEquals(30, road.profit(), "thief debe robar 1/3 de los fondos");
        assertTrue(road.stores[6].getFunds() < 90, "Los fondos de la tienda deben reducirse");
    }
 
    /**
     * Verifica que una tienda autónoma se coloque en una posición aleatoria del camino,
     * ignorando la posición pasada como parámetro al momento de creación.
     */
    @Test
    public void testAutonomousStorePlacedRandomly() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeStore("autonomous", 4, 100);
 
        boolean found = false;
        for (int i = 0; i < road.getLength(); i++) {
            if (road.stores[i] != null && "autonomous".equals(road.stores[i].getType())) {
                found = true;
                break;
            }
        }
 
        assertTrue(found, "Debe haberse colocado una tienda autónoma en alguna posición aleatoria");
    }
 
    /**
     * Verifica la existencia y funcionamiento correcto de una tienda tipo fighter,
     * asegurando que un robot normal pueda interactuar con ella y vaciar sus fondos.
     */
    @Test
    public void testFighterStoreExistsAndInteracts() {
        SilkRoad road = new SilkRoad(17, true);
        road.placeStore("fighter", 10, 100);
        road.placeRobot("normal", 10);
 
        assertEquals(100, road.profit(), "El robot normal debe poder vaciar una fighter si está permitido");
        assertFalse(road.stores[10].isInStock(), "La fighter debe quedar vacía tras ser visitada");
    }
}
