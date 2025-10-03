import java.awt.Color;
import java.lang.Thread;

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

    public Robot(int pos) {
        this.position = pos;
        this.origin = pos;
        this.collectedFunds = 0;
        this.body = new Circle(POSITIONS[pos][0], POSITIONS[pos][1], SIZE, COLORS[pos % COLORS.length]);
        this.body.show(true);
        this.isVisible = true;
    }

    public void moveTo(int steps, boolean reset) {
        if (reset) {
            this.position = origin;
            this.collectedFunds = 0;
        } else {
            this.position += steps;
        }
        this.body.moveTo(POSITIONS[position][0], POSITIONS[position][1]);
    }

    public int getFunds() {
        return collectedFunds;
    }

    public void show(boolean visible) {
        this.isVisible = visible;
        body.show(visible);
    }
    
    public boolean isVisible(){
        return isVisible;
    }
    
    public void collectFunds(int amount) {
        this.collectedFunds += amount;
    }

    public void remove() {
        body.show(false);
    }
    
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


