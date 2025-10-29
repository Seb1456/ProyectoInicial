package silkRoad;

import java.util.*;

public class SilkRoadContest {

    /**
     * Resuelve el problema de la maratón
     */
    public void solve() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
    
        List<Integer> robots = new ArrayList<>();
        List<int[]> stores = new ArrayList<>();
        int[] profits = new int[n];
    
        for (int i = 0; i < n; i++) {
            int type = sc.nextInt();
            int x = sc.nextInt();
    
            if (type == 1) { 
                robots.add(x);
            } else if (type == 2) {
                int c = sc.nextInt();
                stores.add(new int[]{x, c});
            }
    
            int totalProfit = 0;
            for (int[] store : stores) {
                int bestForThisStore = Integer.MIN_VALUE;
                for (int robot : robots) {
                    int profit = store[1] - Math.abs(store[0] - robot);
                    if (profit > bestForThisStore) {
                        bestForThisStore = profit;
                    }
                }
                if (bestForThisStore > 0) {
                    totalProfit += bestForThisStore;
                }
            }
    
            profits[i] = totalProfit;
        }
    
        sc.close();
    
        for (int profit : profits) {
            System.out.println(profit);
        }
    }

    /**
     * Calcula los beneficios día a día, reutilizando la clase SilkRoad (para los tests)
     */
    public int[] computeProfits(int[][] days) {
        List<Integer> robots = new ArrayList<>();
        List<int[]> stores = new ArrayList<>();
        int[] profits = new int[days.length];
    
        for (int i = 0; i < days.length; i++) {
            int type = days[i][0];
            int x = days[i][1];
    
            if (type == 1) { 
                robots.add(x);
            } else if (type == 2) { 
                int c = days[i][2];
                stores.add(new int[]{x, c});
            }
            int totalProfit = 0;
            
            for (int[] store : stores) {
                int bestForThisStore = Integer.MIN_VALUE;
                for (int robot : robots) {
                    int profit = store[1] - Math.abs(store[0] - robot);
                    if (profit > bestForThisStore) {
                        bestForThisStore = profit;
                    }
                }
                if (bestForThisStore > 0) {
                    totalProfit += bestForThisStore;
                }
            }
    
            profits[i] = totalProfit;
        }
    
        return profits;
    }


    /**
     * Simula visualmente  los eventos con entradas del problema.
     * @param slow true para simular más lento
     */
    public void simulate(boolean slow) {
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
        sc.close();

        SilkRoad road = new SilkRoad(17);
        road.makeVisible();
        road.days(days);

        System.out.println("Simulación completada.");
    }
}




