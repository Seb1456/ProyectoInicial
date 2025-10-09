import javax.swing.*;
import java.util.*;

/**
 * Clase que representa la simulación del juego SilkRoad con robots y tiendas.
 * 
 * @author Sebastián Granados - Daniel Valero
 * @version 1.0
 */
public class SilkRoad {
    public static final int MAX_LENGTH = 17;
    public static final int SEGMENT_SIZE = 100;
    public static final int[][] COORDINATES = {
        {0, 0}, {100, 0}, {200, 0}, {300, 0}, {400, 0},
        {400, 100}, {400, 200}, {400, 300}, {400, 400},
        {300, 400}, {200, 400}, {100, 400}, {0, 400},
        {0, 300}, {0, 200}, {100, 200}, {200, 200}
    };

    private static final String TITLE = "SilkRoad";
    private int profit;
    private int fundsTotal;
    private final int roadLength;
    public final Store[] stores;
    private final HashMap<Integer, List<Robot>> robots;
    private final int[] robotPresence;
    private boolean success;
    private boolean testing = false;
    
    /**
     * Constructor principal de SilkRoad.
     * @param length longitud de la carretera (número de posiciones).
     */
    public SilkRoad(int length) {
        this.roadLength = length;
        this.stores = new Store[MAX_LENGTH];
        this.robots = new HashMap<>();
        this.robotPresence = new int[MAX_LENGTH];
        this.profit = 0;
        this.fundsTotal = 0;
        this.success = true;

        for (int i = 0; i < MAX_LENGTH; i++) {
            stores[i] = null;
            robots.put(i, new ArrayList<>());
            robotPresence[i] = 0;
        }

        for (int i = 0; i < length; i++) {
            new Road(COORDINATES[i][0], COORDINATES[i][1]);
        }
    }
    
    /**
     * Constructor de SilkRoad con modo de prueba.
     * @param length longitud de la carretera.
     * @param testing si es true, desactiva mensajes gráficos.
     */
    public SilkRoad(int length, boolean testing) {
        this(length);
        this.testing = testing;
    }
    /**
     * Muestra un mensaje al usuario.
     * @param msg mensaje a mostrar
     * @param type tipo de mensaje de JOptionPane
     */
    private void notifyUser(String msg, int type) {
        if (!testing) {
            JOptionPane.showMessageDialog(null, msg, TITLE, type);
        }
    }
    /**
     * Muestra un mensaje de información al usuario.
     * @param msg mensaje a mostrar
     */
    private void notifyUser(String msg) {
        notifyUser(msg, JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Retorna la longitud máxima de la carretera.
     * @return longitud máxima
     */
    public int getLength(){
        return MAX_LENGTH;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
    
        int n = sc.nextInt();
        int[][] days = new int[n][];
    
        for (int i = 0; i < n; i++) {
            int type = sc.nextInt();
            int pos = sc.nextInt();
            if (type == 1) {
                days[i] = new int[]{type, pos};
            } else {
                int funds = sc.nextInt();
                days[i] = new int[]{type, pos, funds};
            }
        }
        SilkRoadContest contest = new SilkRoadContest();
        int[] profits = contest.solve(days);
        for (int profit : profits){
        System.out.println(profit);
        }
        sc.close();
    }
    /**
     * Procesa una serie de eventos de días.
     * @param events matriz de eventos, cada fila es {tipo, posición, [fondos]}
     */
    public void days(int[][] events) {
        reboot();
        int n = events.length;
    
        for (int day = 0; day < n; day++) {
            int[] event = events[day];
            if (event.length < 2) {
                success = false;
                return;
            }
    
            int type = event[0];
            int position = event[1];
    
            if (type == 1) {
                placeRobot(position);
            } else if (type == 2) {
                if (event.length < 3) {
                    success = false;
                    return;
                }
                int funds = event[2];
                placeStore(position, funds);
            } else {
                success = false;
                return;
            }
    
            reboot();
        }
    
        success = true;
    }
    /**
     * Coloca una tienda en una posición específica.
     * @param pos posición en la carretera
     * @param funds cantidad de fondos disponibles en la tienda
     */
    public void placeStore(int pos, int funds) {
        if (pos < 0 || pos >= roadLength || funds <= 0) {
            notifyUser("Invalid store parameters", JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        if (stores[pos] != null) {
            notifyUser("Store exists at position " + pos, JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        fundsTotal += funds;
        stores[pos] = new Store(funds, pos);
        updateBar();
        success = true;
        System.out.println("Store placed at " + pos + ", total funds: " + fundsTotal);
    }
    /**
     * Elimina la tienda de una posición específica.
     * @param pos posición en la carretera
     */
    public void removeStore(int pos) {
        if (pos < 0 || pos >= roadLength) {
            notifyUser("Invalid position", JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        if (stores[pos] == null) {
            notifyUser("No store at position " + pos, JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        fundsTotal -= stores[pos].getFunds();
        stores[pos].remove();
        stores[pos] = null;
        updateBar();
        success = true;
        System.out.println("Store removed from " + pos + ", total funds: " + fundsTotal);
    }
    /**
     * Coloca un robot en una posición específica.
     * @param pos posición del robot
     */
    public void placeRobot(int pos) {
        if (pos < 0 || pos >= roadLength) {
            notifyUser("Invalid position", JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        if (robotPresence[pos] == 1) {
            notifyUser("Robot already at position " + pos, JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        Robot robot = new Robot(pos);
        robotPresence[pos] = 1;
        robots.get(pos).add(robot);

        if (stores[pos] != null && stores[pos].isInStock()) {
            int funds = stores[pos].getFunds();
            robot.addProfit(funds);
            stores[pos].depleteStock();
            stores[pos].changeColor();
            stores[pos].incrementEmptiedCount();
            profit += funds;
            updateBar();
        }
        success = true;
    }
    /**
     * Elimina un robot de una posición específica.
     * @param pos posición del robot
     */
    public void removeRobot(int pos) {
        if (pos < 0 || pos >= roadLength) {
            notifyUser("Invalid position", JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        if (robots.get(pos).isEmpty()) {
            notifyUser("No robot at position " + pos, JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        Robot robot = robots.get(pos).remove(0);
        robot.remove();
        robotPresence[pos] = 0;
        success = true;
    }
    /**
     * Mueve un robot desde una posición actual.
     * @param pos posición inicial del robot
     * @param steps cantidad de pasos a mover (positivos o negativos)
     */
    public void moveRobot(int pos, int steps) {
        if (pos < 0 || pos >= roadLength || pos + steps < 0 || pos + steps >= roadLength) {
            notifyUser("Invalid move parameters", JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        if (robots.get(pos).isEmpty()) {
            notifyUser("No robot at position " + pos, JOptionPane.ERROR_MESSAGE);
            success = false;
            return;
        }
        Robot robot = robots.get(pos).remove(0);
        int direction = steps > 0 ? 1 : -1;
        int stepsAbs = Math.abs(steps);
        for (int i = 1; i <= stepsAbs; i++) {
            int newPos = pos + i * direction;

            robot.moveTo(direction, false);

            if (stores[newPos] != null && stores[newPos].isInStock()) {
                int funds = stores[newPos].getFunds();
                robot.addProfit(funds);
                stores[newPos].depleteStock();
                stores[newPos].changeColor();
                if (this.stores[pos] != null){
                    stores[pos].incrementEmptiedCount();
                }
                profit += funds;
                updateBar();
            }
        }
        int finalPos = pos + steps;
        robots.get(finalPos).add(robot);
        success = true;
    }
    /**
     * Reabastece todas las tiendas vacías.
     */
    public void resupplyStores() {
        for (int i = 0; i < roadLength; i++) {
            Store store = stores[i];
            if (store != null) {
                if (!store.isInStock()) {
                    store.restock();
                    store.resetColor();
                    fundsTotal += store.getFunds();
                }
            }
        }
        updateBar();
        success = true;
    }
    /**
     * Devuelve todos los robots a su posición inicial.
     */
    public void returnRobots() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (Robot robot : robots.get(i)) {
                robot.moveTo(0, true);
            }
        }
        success = true;
    }
    /**
     * Reinicia la simulación.
     */
    public void reboot() {
        resupplyStores();
        returnRobots();
        profit = 0;
        fundsTotal = 0;
        updateBar();
        success = true;
    }
    /**
     * Devuelve la ganancia total actual.
     * @return ganancia total
     */
    public int profit() {
        success = true;
        return profit;
    }
    /**
     * Devuelve los datos de todas las tiendas.
     * @return matriz [posición, fondos]
     */
    public int[][] stores() {
        int[][] data = new int[MAX_LENGTH][2];
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (stores[i] != null) {
                data[i] = new int[]{i, stores[i].getFunds()};
            }
        }
        success = true;
        return data;
    }
    /**
     * Devuelve todos los robots.
     * @return lista de robots
     */
    public List<Robot> getAllRobots() {
        List<Robot> currentRobots = new ArrayList<>();
        for (int i = 0; i < MAX_LENGTH; i++) {
            currentRobots.addAll(robots.get(i));
        }
        success = true;
        return currentRobots;
    }
    /**
     * Muestra todos los objetos gráficos.
     */
    public void makeVisible() {
        if (testing) return;
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (Robot robot : robots.get(i)) {
                robot.show(true);
            }
            if (stores[i] != null) {
                stores[i].show(true);
            }
        }
        success = true;
    }
    /**
     * Oculta todos los objetos gráficos.
     */
    public void makeInvisible() {
        if (testing) return;
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (Robot robot : robots.get(i)) {
                robot.show(false);
            }
            if (stores[i] != null) {
                stores[i].show(false);
            }
        }
        success = true;
    }
    /**
     * Finaliza la simulación y cierra la aplicación.
     */
    public void finish() {
        success = true;
        System.exit(0);
    }
    /**
     * Devuelve el número de tiendas activas.
     * @return cantidad de tiendas
     */
    public int getStoreCount() {
        int count = 0;
        for (Store s : stores) {
            if (s != null) count++;
        }
        return count;
    }
    /**
     * Devuelve el número de robots activos.
     * @return cantidad de robots
     */
    public int getRobotCount() {
        int count = 0;
        for (int i = 0; i < MAX_LENGTH; i++) {
            count += robots.get(i).size();
        }
        return count;
    }
    /**
     * Indica si la última operación fue exitosa.
     * @return true si tuvo éxito, false si falló
     */
    public boolean ok() {
        notifyUser(success ? "Success" : "Failed", success ? JOptionPane.PLAIN_MESSAGE : JOptionPane.ERROR_MESSAGE);
        return success;
    }
    /**
     * Actualiza la barra de progreso en Canvas.
     */
    public void updateBar() {
        int percentage = fundsTotal == 0 ? 0 : (int) ((double) profit / fundsTotal * 100);
        Canvas.setProgress(percentage);
    }
    /**
     * Marca el robot con mayor ganancia haciendo parpadear.
     */
    public void bestProfit() {
        List<Robot> datos = getAllRobots();

        Robot ganador = null;
        int maxProfit = Integer.MIN_VALUE;

        for (Robot r : datos) {
            int profit = r.getFunds();
            if (profit > maxProfit) {
                maxProfit = profit;
                ganador = r;
            }
        }

        if (ganador != null) {
            ganador.blink(10);
        }

        success = true;
    }
    /**
     * Obtiene la cantidad de veces que cada tienda ha sido vaciada.
     * @return arreglo con conteo de tiendas vaciadas
     */
    public int[] getStoreEmptyCounts() {
        int[] counts = new int[roadLength];
        for (int i = 0; i < roadLength; i++) {
            if (stores[i] != null) {
            counts[i] = stores[i].getEmptiedCount();
            }
        }
        return counts;
    }
    /**
     * Devuelve información de las tiendas vacías.
     * @return matriz [posición, fondos] de tiendas vacías
     */
    public int[][] emptiedStores() {
        List<Store> emptiedStores = new ArrayList<>();
        for (int i = 0; i < roadLength; i++) {
            Store store = stores[i];
            if (store != null && !store.isInStock()) {
                emptiedStores.add(store);
            }
        }

        emptiedStores.sort((s1, s2) -> Integer.compare(s1.getPosition(), s2.getPosition()));

        int[][] result = new int[emptiedStores.size()][2];
        for (int i = 0; i < result.length; i++) {
            Store store = emptiedStores.get(i);
            result[i][0] = store.getPosition();
            result[i][1] = store.getFunds();
        }
        if (!emptiedStores.isEmpty()) {
            StringBuilder positionsMsg = new StringBuilder("Las empty stores están en las posiciones: ");
            for (int i = 0; i < emptiedStores.size(); i++) {
                positionsMsg.append(emptiedStores.get(i).getPosition());
                if (i < emptiedStores.size() - 1) {
                    positionsMsg.append(", ");
                }
            }
            notifyUser(positionsMsg.toString(), JOptionPane.INFORMATION_MESSAGE);
        } else {
            notifyUser("No hay tiendas vacías.", JOptionPane.INFORMATION_MESSAGE);
        }

        return result;
    }
    /**
     * Devuelve la ganancia de cada robot por movimiento.
     * @return matriz con cada fila [posición inicial, ganancias por movimiento...]
     */
        public int[][] profitPerMove() {
        List<Robot> allRobots = getAllRobots();
        List<int[]> resultList = new ArrayList<>();
    
        for (Robot r : allRobots) {
            List<Integer> gains = r.getProfitHistory();
            int[] row = new int[gains.size() + 1];
            row[0] = r.getPosition();
            for (int i = 0; i < gains.size(); i++) {
                row[i + 1] = gains.get(i);
            }
            resultList.add(row);
        }
    
        resultList.sort(Comparator.comparingInt(a -> a[0]));
    
        int[][] result = new int[resultList.size()][];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
    
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    
        return result;
    }
    /**
     * Mueve todos los robots automáticamente hacia la mejor tienda.
     */
    public void moveRobots() {
        for (int pos = 0; pos < roadLength; pos++) {
            if (!robots.get(pos).isEmpty()) {
                Robot robot = robots.get(pos).get(0);
                int bestPos = -1;
                int maxFunds = -1;
                for (int i = 0; i < roadLength; i++) {
                    if (stores[i] != null && stores[i].isInStock() && stores[i].getFunds() > maxFunds) {
                        bestPos = i;
                        maxFunds = stores[i].getFunds();
                    }
                }
                if (bestPos != -1 && bestPos != pos) {
                    moveRobot(pos, bestPos - pos);
                }
            }
        }
    }
}
    

        





