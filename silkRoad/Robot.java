import java.awt.Color;
import java.lang.Thread;
import java.util.*;

/**
 * Representa un robot en la carretera SilkRoad que recoge fondos de las tiendas.
 */
public class Robot {
    private static final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN};
    private static final int[][] POSITIONS = {
        {50, 50}, {150, 50}, {250, 50}, {350, 50}, {450, 50},
        {450, 150}, {450, 250}, {450, 350}, {450, 450},
        {350, 450}, {250, 450}, {150, 450}, {50, 450},
        {50, 350}, {50, 250}, {150, 250}, {250, 250}
    };
    private static final int SIZE = 25;

    private final int origin;
    private int position;
    private int collectedFunds;
    private final Circle body;
    private boolean isVisible;
    private List<Integer> profitHistory = new ArrayList<>();
    
    /**
     * Constructor del robot.
     * @param pos posición inicial del robot
     */
    public Robot(int pos) {
        this.position = pos;
        this.origin = pos;
        this.collectedFunds = 0;
        this.body = new Circle(POSITIONS[pos][0], POSITIONS[pos][1], SIZE, COLORS[pos % COLORS.length]);
        this.body.show(true);
        this.isVisible = true;
    }
    /**
     * Mueve el robot.
     * @param steps cantidad de pasos a mover
     * @param reset si es true, lo devuelve a la posición inicial y reinicia fondos
     */
    public void moveTo(int steps, boolean reset) {
        if (reset) {
            this.position = origin;
            this.collectedFunds = 0;
        } else {
            this.position += steps;
        }
        this.body.moveTo(POSITIONS[position][0], POSITIONS[position][1]);
    }
    /**
     * Obtiene los fondos recolectados por el robot.
     * @return fondos recolectados
     */
    public int getFunds() {
        return collectedFunds;
    }
    /**
     * Obtiene la posición actual del robot.
     * @return posición actual
     */
    public int getPosition(){
        return position;
    }
    /**
     * Muestra u oculta el robot.
     * @param visible true para mostrar, false para ocultar
     */
    public void show(boolean visible) {
        this.isVisible = visible;
        body.show(visible);
    }
    /**
     * Indica si el robot está visible.
     * @return true si es visible
     */
    public boolean isVisible(){
        return isVisible;
    }
    /**
     * Agrega ganancia al robot y la registra en historial.
     * @param amount cantidad de fondos recolectados
     */
    public void addProfit(int amount) {
        collectedFunds += amount;
        profitHistory.add(amount);
    }
    /**
     * Obtiene el historial de ganancias del robot.
     * @return lista de ganancias por movimiento
     */
    public List<Integer> getProfitHistory() {
        return new ArrayList<>(profitHistory);
    }
    /**
     * Suma fondos directamente al robot.
     * @param amount cantidad de fondos
     */
    public void collectFunds(int amount) {
        this.collectedFunds += amount;
    }
    /**
     * Elimina el robot visualmente.
     */
    public void remove() {
        body.show(false);
    }
    /**
     * Hace parpadear el robot para destacarlo.
     * @param times número de parpadeos
     */
    public void blink(int times) {
        if (isVisible) {
            boolean visible = true;
            for (int i = 0; i < times; i++) {
                visible = !visible;
                show(visible);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Main thread interrupted during sleep.");
                    Thread.currentThread().interrupt();
                }
            }
            show(true); 
        }
    }
}


