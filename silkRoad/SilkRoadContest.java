public class SilkRoadContest
{
    private int[] profits;

    /**
     * Determina cada posición de un arreglo y su descripción y funcionalidad.
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
     * Resuelve el problema de la maratón.
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
     * Simula el problema de la silkroad.
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