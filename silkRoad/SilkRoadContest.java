import java.util.*;
/**
 * Clase para manejar eventos y resolver la simulación de SilkRoad.
 */
public class SilkRoadContest
{
    private int[] profits;

    /**
     * Procesa un evento en SilkRoad.
     * @param road instancia de SilkRoad
     * @param event arreglo que contiene {tipo, posición, [fondos]}
     */
    private void procesarEvento(SilkRoad road, int[] event){
        int type = event[0];
        int position = event[1];
    
        if (type == 1){
            road.placeRobot(position);
        }
        else if (type == 2){
            int tenges = event[2];
            road.placeStore(position, tenges);
        }
    
    }
    /**
     * Resuelve la simulación y devuelve la ganancia por día.
     * @param days matriz de eventos
     * @return arreglo con ganancias por día
     */
    public int[] solve(int[][] days) {
        SilkRoad road = new SilkRoad(SilkRoad.MAX_LENGTH, false);
        int n = days.length;
        int[] profits = new int[n];
        
        for (int day = 0; day < n; day++){
            int[] event = days[day];
            procesarEvento(road, event);
            road.moveRobots();
            profits[day] = road.profit();
        }
        return profits;
    }
    /**
     * Simula SilkRoad con visualización.
     * @param days matriz de eventos
     * @param slow true para simulación lenta
     */
    public void simulate(int[][] days, boolean slow) {
        SilkRoad road = new SilkRoad(SilkRoad.MAX_LENGTH, false);

        int delay = slow ? 1500 : 500;

        for (int day = 0; day < days.length; day++) {
            int[] event = days[day];
            procesarEvento(road, event);
            road.moveRobots();

            int[][] profits = road.profitPerMove();

            int totalProfit = 0;
            for (int[] row : profits) {
                for (int i = 1; i < row.length; i++) totalProfit += row[i];
            }
            if (totalProfit < 50) {
                road.placeStore(day % road.getLength(), 30);
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}