import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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

    private final int funds;
    private boolean inStock;
    private final int position;
    private final Rectangle facade;
    private final Triangle roof;
    private Color initialColor;
    private int emptiedCount = 0; 
    
    /**
     * Constructor de la tienda.
     * @param money fondos de la tienda
     * @param pos posición de la tienda
     */
    public Store(int money, int pos) {
        this.funds = money;
        this.position = pos;
        this.inStock = true;
        Color color = COLORS[pos % COLORS.length];
        this.facade = new Rectangle(POSITIONS[pos][0], POSITIONS[pos][1], SIZE, SIZE, color);
        this.roof = new Triangle(POSITIONS[pos][2], POSITIONS[pos][3], SIZE, SIZE, color.darker());
        this.facade.show(true);
        this.roof.show(true);
        this.initialColor = color;
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
        this.inStock = false;
    }
    /**
     * Devuelve los fondos de la tienda.
     * @return cantidad de fondos
     */
    public int getFunds() {
        return funds;
    }
    /**
     * Indica si la tienda tiene stock.
     * @return true si tiene stock
     */
    public boolean isInStock() {
        return inStock;
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
}

