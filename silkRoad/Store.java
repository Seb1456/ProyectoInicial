import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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

    public void restock() {
        this.inStock = true;
    }
    
    public void incrementEmptiedCount() {
    emptiedCount++;
    }

    public int getEmptiedCount() {
    return emptiedCount;
    }

    public void show(boolean visible) {
        facade.show(visible);
        roof.show(visible);
    }

    public void depleteStock() {
        this.inStock = false;
    }

    public int getFunds() {
        return funds;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void remove() {
        facade.show(false);
        roof.show(false);
    }
    public int getPosition() {
        return position;
    }
    
    public void changeColor() {
        Color newColor = Color.BLACK;
        facade.setColor(newColor);
        roof.setColor(newColor.darker());
    }
    
    public void resetColor() {
        this.facade.setColor(this.initialColor);
        this.roof.setColor(this.initialColor.darker());
    }
}

