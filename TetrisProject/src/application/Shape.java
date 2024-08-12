package application;
import javafx.scene.paint.Color;
public class Shape {
	private int[][] shape;
    private Color color;
    private int x, y; 
    private static final int WIDTH = 10;

    public Shape(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.x = WIDTH / 2 - shape[0].length / 2; 
        this.y = 0;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getHeight() {
        return shape.length;
    }
    public int getWidth() {
        return shape[0].length;
    }
    public void rotate() {
			int[][] rotate = new int[shape[0].length][shape.length];
			for(int i = 0; i < shape.length;i++) {
				for(int j = 0; j < shape[0].length; j++) {
					rotate[j][shape.length - 1 - i] = shape[i][j];
				}
			}
			shape = rotate;
    	}
    }
