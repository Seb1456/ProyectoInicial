package silkRoad;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import shapes.*;

/**
 * Representa una tienda en SilkRoad.
 */
public class Store {
    public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.BLACK};
    private static final int[][] POSITIONS = {
        {75, 0, 50, 12}, {175, 0, 150, 12}, {275, 0, 250, 12},
        {375, 0, 350, 12}, {475, 0, 450, 12}, {475, 125, 450, 137},
        {475, 225, 450, 237}, {475, 325, 450, 337}, {475, 475, 450, 487},
        {375, 475, 350, 487}, {275, 475, 250, 487}, {175, 475, 150, 487},
        {75, 475, 50, 487}, {25, 325, 0, 337}, {25, 225, 0, 237},
        {150, 200, 125, 212}, {250, 200, 225, 212}
    };
    private static final int SIZE = 25;

    private boolean inStock;
    private final int position;
    private final Rectangle facade;
    private final Triangle roof;
    private Color initialColor;
    private int emptiedCount = 0; 
    private final String type;
    private int currentFunds;
    
    /**
     * Constructor de la tienda.
     * @param type tipo de tienda
     * @param money fondos de la tienda
     * @param pos posición de la tienda
     */
    public Store(String type, int money, int pos) {
        this.currentFunds = money;
        this.position = pos;
        this.type = type;
        this.inStock = true;
        Color color;
        if ("normal".equals(type)) color = Color.ORANGE;
        else if ("autonomous".equals(type)) color = Color.MAGENTA;
        else if ("fighter".equals(type)) color = Color.CYAN;
        else color = Color.YELLOW;
        this.facade = new Rectangle(POSITIONS[pos][0], POSITIONS[pos][1], SIZE, SIZE, color);
        this.roof = new Triangle(POSITIONS[pos][3], POSITIONS[pos][2], SIZE, SIZE, color.darker());
        this.facade.show(true);
        this.roof.show(true);
        this.initialColor = color;
    }
    
    /**
     * Roba cierta cantidad de fondos de la tienda.
     * @param amount cantidad a robar
     * @return cantidad realmente robada
     */
    public int rob(int amount) {
        if (!inStock) return 0;
        
        int stolen = Math.min(amount, currentFunds);
        currentFunds -= stolen;
        if (currentFunds <= 0) {
            depleteStock();
            incrementEmptiedCount();
            changeColor();
        }
        return stolen;
    }

    /**
     * Reabastece la tienda.
     */
    public void restock() {
        this.inStock = true;
    }
    /**
     * Incrementa el contador de veces que la tienda se vació.
     */
    public void incrementEmptiedCount() {
    emptiedCount++;
    }
    /**
     * Devuelve el contador de veces que la tienda se vació.
     * @return número de veces vaciada
     */
    public int getEmptiedCount() {
    return emptiedCount;
    }
    /**
     * Muestra u oculta la tienda.
     * @param visible true para mostrar, false para ocultar
     */
    public void show(boolean visible) {
        facade.show(visible);
        roof.show(visible);
    }
    /**
     * Vacía el stock de la tienda.
     */
    public void depleteStock() {
        this.currentFunds = 0;
        this.inStock = false;
    }
    /**
     * Devuelve los fondos de la tienda.
     * @return cantidad de fondos
     */
     public int getFunds() {
         return currentFunds;
    }

    /**
     * Indica si la tienda tiene stock.
     * @return true si tiene stock
     */
    public boolean isInStock() {
        return inStock;
    }
    /**
     * Indica el tipo de tienda.
     * @return el tipo de la tienda
     */
    public String getType() {
        return type;
    }
    /**
     * Oculta la tienda visualmente.
     */
    public void remove() {
        facade.show(false);
        roof.show(false);
    }
    /**
     * Devuelve la posición de la tienda.
     * @return posición
     */
    public int getPosition() {
        return position;
    }
    /**
     * Cambia el color de la tienda al color vacío.
     */
    public void changeColor() {
        Color newColor = Color.BLACK;
        facade.setColor(newColor);
        roof.setColor(newColor.darker());
    }
    /**
     * Resetea el color de la tienda al original.
     */
    public void resetColor() {
        this.facade.setColor(this.initialColor);
        this.roof.setColor(this.initialColor.darker());
    }
    /**
     * Define si es posible ser robada una tienda tipo fighter o no.
     */
    public boolean canBeRobbedBy(Robot robot){
        if ("fighter".equals(type)){
            return robot.getFunds() > this.getFunds();
        }
        return true;
    }
}

