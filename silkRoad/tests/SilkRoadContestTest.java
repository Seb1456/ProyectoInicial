package tests;

import silkRoad.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SilkRoadContestTest {

    @Test
    public void ComputeProfitsBasic() {
        SilkRoadContest contest = new SilkRoadContest();

        int[][] days = {
            {1, 2},
            {2, 3, 10}, 
            {1, 5},     
            {2, 8, 6},  
            {2, 2, 8}   
        };

        int[] result = contest.computeProfits(days);
        int[] expected = {0, 9, 9, 12, 20};

        assertArrayEquals(expected, result);
    }

    @Test
    public void NoRobots() {
        SilkRoadContest contest = new SilkRoadContest();

        int[][] days = {
            {2, 5, 10},
            {2, 8, 7}
        };

        int[] result = contest.computeProfits(days);
        assertArrayEquals(new int[]{0, 0}, result);
    }

    @Test
    public void OnlyRobots() {
        SilkRoadContest contest = new SilkRoadContest();

        int[][] days = {
            {1, 1},
            {1, 3}
        };

        int[] result = contest.computeProfits(days);
        assertArrayEquals(new int[]{0, 0}, result);
    }
}
