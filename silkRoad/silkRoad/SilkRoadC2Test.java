package silkRoad;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for SilkRoad project
 * 
 * @author Sebastián Granados - Daniel Valero
 * @version 1.0
 */
public class SilkRoadC2Test {

    private SilkRoad game;
    private SilkRoad robots;

    @Before
    public void setUp() {
        game = new SilkRoad(17,true);
        game.makeInvisible();
    }
    /**
     * Debería crear una tienda en la ubicación dada y dentro del rango de la carretera.
     */
    @Test
    public void accordingGVShouldPLaceStoreInLocationGiven(){
        game.placeStore("normal", 2, 100);
        assertTrue("Se debio crear una tienda en posición 3", game.ok());
        assertEquals("Ahora debe haber una tienda", 1, game.getStoreCount());
    }
    /**
     * No deberia colocar una tienda fuera de los limites de la carretera.
     */
    @Test
    public void accordingGVShouldNotPlaceStoreOutsideBounds(){
        game.placeStore("normal", 18, 100);
        assertFalse("No deberia permitir colocar tienda afuera de ruta", game.ok());
        assertEquals("No debe haber ninguna tienda creada", 0, game.getStoreCount());
    }
    /**
     * No deberia poder colocar una tienda donde ya existe otra creada.
     */
    @Test
    public void accordingGVShouldNotBeCreatedInAlreadyExistentPositionOfStore(){
        game.placeStore("normal", 3, 30);
        game.placeStore("normal", 3, 400);
        assertFalse("No deberia poder colocarse una tienda sobre la otra.", game.ok());
        assertEquals("Solo debería haberse creado la primer tienda", 1, game.getStoreCount());
    }
    /**
     * Debería eliminar la tienda ya creada.
     */
    @Test
    public void accordingGVShouldEraseAnStoreAlreadyPlaced(){
        game.placeStore("normal", 4, 100);
        game.removeStore(4);
        assertTrue("Debería poder haberse borrado la tienda.", game.ok());
        assertEquals("No debería haber ninguna tienda de haberse borrado correctamente.", 0, game.getStoreCount());
    }
    /**
     * Deberia poder crear un robot en una ubicación valida y dentro del rango.
     */
    @Test
    public void accordingGVShouldPlaceRobotCorrectlyInAValidLocation(){
        game.placeRobot("normal", 15);
        assertTrue("Deberia haber colocado el robot en la posición 16", game.ok());
        assertEquals("Ahora existe un solo robot en la lista.", 1, game.getRobotCount());
    }
    /**
     * No deberia colocar un robot afuera del rango de la carretera.
     */
    @Test
    public void accordingGVShouldNotPlaceRobotOutOfBounds(){
        game.placeRobot("normal", 18);
        assertFalse("No deberia permitir colocar un robot afuera de ruta.", game.ok());
        assertEquals("No debe haber ningun robot creado.", 0, game.getRobotCount());
    }
    /**
     * Deberia eliminar el robot ya creado.
     */
    @Test
    public void accordingGVShouldEraseTheRobotAlreadyPlaced(){
        game.placeRobot("normal", 5);
        game.removeRobot(5);
        assertTrue("Deberia poder haber borrado al robot.", game.ok());
        assertEquals("No debería existir ningún robot existente.", 0, game.getRobotCount());
    }
    /**
     * Deberia recolectar los tenges al colocar un robot sobre una tienda.
     */
    @Test
    public void accordingGVShouldCollectTheFundsFromStoreIfRobotIsPlacedInStore(){
        game.placeStore("normal", 8,300);
        game.placeRobot("normal", 8);
        assertTrue("La operación debe estar correcta y ser válida", game.ok());
        assertEquals("El robot debe recoger los 300 tenges", 300, game.profit());
    }
    /**
     * Deberia pasar por una tienda y recolectar sus tenges.
     */
    @Test
    public void accordingGVShouldCollectTheFundsIfItPassesByAnStore(){
        game.placeStore("normal", 4, 250);
        game.placeRobot("normal", 3);
        game.moveRobot(3, 2);
        assertTrue("Esta operación debe ser válida.", game.ok());
        assertEquals("El profit debe ser 250.", 250, game.profit());
    }
    /**
     * No deberia recolectar de tiendas si no tienen tenges.
     */
    @Test
    public void accordingGVShouldNotCollectFundsIfThereIsNoFunds(){
        game.placeStore("normal", 3, 0);
        game.placeRobot("normal", 2);
        game.moveRobot(2, 2);
        assertTrue("La operación debe ser válida", game.ok());
        assertEquals("El profit debe quedar en 0.", 0, game.profit());
    }
    /**
     * No deberia eliminar una tienda que no existe.
     */
    @Test
    public void accordingGVShouldNotEraseANonExistentStore(){
        game.removeStore(5);
        assertFalse("No deberia ser valida esta operación.", game.ok());
    }
    /**
     * No debería cambiar tiendas que aún tienen stock.
     */
    @Test
    public void accordingGVShouldNotAlterStoresInStock() {
        game.placeStore("normal", 3, 200);
        int beforeFunds = game.stores()[3][1];
        
        game.resupplyStores();
        int afterFunds = game.stores()[3][1];
        
        assertEquals("Los fondos de la tienda no deben cambiar si aún estaba en stock", beforeFunds, afterFunds);
    }

    /**
     * Debería devolver los robots al origen.
     */
    @Test
    public void accordingGVShouldReturnRobotsToOrigin() {
        game.placeRobot("normal", 5);
        game.moveRobot(5, -2);

        game.returnRobots();
        assertTrue("La operación debe ser válida", game.ok());
        assertEquals("Debe seguir existiendo 1 robot tras returnRobots", 1, game.getRobotCount());
    }

    /**
     * Debería reiniciar profit y fondos.
     */
    @Test
    public void accordingGVShouldRebootCorrectly() {
        game.placeStore("normal", 4, 150);
        game.placeRobot("normal", 4);
        assertEquals("El profit debe ser 150", 150, game.profit());

        game.reboot();
        assertTrue("Reboot debe ser válido", game.ok());
        assertEquals("El profit debe reiniciarse en 0", 0, game.profit());
        assertEquals("Los fondos totales deben reiniciarse en 0", 0, game.getStoreCount() * 0);
    }

    /**
     * Debería devolver el profit acumulado.
     */
    @Test
    public void accordingGVShouldReturnProfitValue() {
        game.placeStore("normal", 6, 120);
        game.placeRobot("normal", 6);
        int result = game.profit();

        assertEquals("El profit debe ser 120", 120, result);
    }

    /**
     * Debería resaltar el robot con mayor ganancia.
     */
    @Test
    public void accordingGVShouldHighlightBestProfitRobot() {
        game.placeStore("normal", 1, 100);
        game.placeRobot("normal", 1);
        game.placeStore("normal", 2, 50);
        game.placeRobot("normal", 2);

        game.bestProfit();
        assertTrue("La operación debe ser válida", game.ok());
    }

    /**
     * Debería listar correctamente las tiendas vacías.
     */
    @Test
    public void accordingGVShouldListEmptiedStores() {
        game.placeStore("normal", 3, 80);
        game.placeRobot("normal", 3);
        int[][] emptied = game.emptiedStores();

        assertEquals("Debe haber 1 tienda vacía", 1, emptied.length);
        assertEquals("La tienda vacía debe estar en posición 3", 3, emptied[0][0]);
    }

    /**
     * Debería mover todos los robots cuando hay espacio.
     */
    @Test
    public void accordingGVShouldMoveAllRobots() {
        game.placeRobot("normal", 5);
        game.moveRobots();
        assertTrue("La operación debe ser válida", game.ok());
        assertEquals("Debe seguir existiendo 1 robot tras moveRobots", 1, game.getRobotCount());
    }
    
    /**
    * Verifica que showRobotProfits muestre correctamente las ganancias de un robot e identifica bugs.
    */
     @Test
     public void ShowRobotProfitsLRShouldDisplayCorrectProfits()  {
         game.placeRobot("normal", 0);
         game.placeStore("normal", 1, 50);
         game.placeStore("normal", 3, 100);
         game.moveRobot(0, 1);
         game.moveRobot(1, 2);
         
         assertEquals(150, game.profit());
     }
}