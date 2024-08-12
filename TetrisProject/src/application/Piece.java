package application;
import java.util.Random;
import javafx.scene.paint.Color;
public class Piece {

	private static final int[][][] SHAPES = {
	        {{1, 1},
	         {1, 1}}, // O

	        {{1, 1, 1, 1}}, // I

	        {{0, 1, 1},
	         {1, 1, 0}}, // S

	        {{1, 1, 0},
	         {0, 1, 1}}, // Z

	        {{1, 0},
	         {1, 0},
	         {1, 1}}, // L

	        {{0, 1},
	         {0, 1},
	         {1, 1}}, // J

	        {{0, 1, 0},
	         {1, 1, 1}} // T
	    };
	private final Color[] COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PURPLE, Color.ORANGE};

    public int[][] getShape() {
    	Random s = new Random();
    	int shape = s.nextInt(SHAPES.length);
    	return SHAPES[shape];
    }

    public Color getColor() {
    	 Random c = new Random();
		 int color = c.nextInt(COLORS.length);
		 return COLORS[color];
    }
}

