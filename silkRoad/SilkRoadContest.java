
/**
 * Write a description of class SilkRoadContest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SilkRoadContest
{
    // instance variables - replace the example below with your own
    private int[] profits;

    /**
     * Constructor for objects of class SilkRoadContest
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
    public int[] solve(int[][] days) {
        SilkRoad road = new SilkRoad(SilkRoad.MAX_LENGTH, true);
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
    
    public void simulate(int[][] days, boolean slow){
        SilkRoad road = new SilkRoad(SilkRoad.MAX_LENGTH, false);
        road.makeVisible();
        int delay = slow ? 1500: 500;
        for (int day = 0; day < days.length; day++){
            int[] event = days[day];
            if (event[0] == 1){
                road.placeRobot(event[1]);
            }
            else if (event[0] == 2){
                road.placeStore(event[1], event[2]);
            }
            road.moveRobots();
            System.out.println("Día" +(day + 1) +"- Profit acumulado:" + road.profit());
            
            try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
        }
        road.bestProfit();
        road.ok();
    }
}