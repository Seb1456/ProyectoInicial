package silkRoad;

import java.awt.Color;
import shapes.*;

/**
 * Clase abstracta que representa una tienda en SilkRoad.
 */
public abstract class Store {
    public static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.BLACK};
    protected static final int[][] POSITIONS = {
        {75, 0, 50, 12}, {175, 0, 150, 12}, {275, 0, 250, 12},
        {375, 0, 350, 12}, {475, 0, 450, 12}, {475, 125, 450, 137},
        {475, 225, 450, 237}, {475, 325, 450, 337}, {475, 475, 450, 487},
        {375, 475, 350, 487}, {275, 475, 250, 487}, {175, 475, 150, 487},
        {75, 475, 50, 487}, {25, 325, 0, 337}, {25, 225, 0, 237},
        {150, 200, 125, 212}, {250, 200, 225, 212}
    };
    protected static final int SIZE = 25;

    protected boolean inStock;
    protected final int position;
    protected final Rectangle facade;
    protected final Triangle roof;
    protected Color initialColor;
    protected int emptiedCount = 0; 
    protected final String type;
    protected int currentFunds;
    
    /**
     * Constructor de la tienda.
     * @param type tipo de tienda
     * @param money fondos de la tienda
     * @param pos posición de la tienda
     * @param color color de la tienda
     */
    protected Store(String type, int money, int pos, Color color) {
        this.currentFunds = money;
        this.position = pos;
        this.type = type;
        this.inStock = true;
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
     * Método abstracto para determinar si un robot puede robar de esta tienda.
     * @param robot robot que intenta robar
     * @return true si puede ser robada
     */
    public abstract boolean canBeRobbedBy(Robot robot);
}

