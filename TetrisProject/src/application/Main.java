package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



public class Main extends Application {
	
    private static final int BLOCK_SIZE = 30; // Size of each block
    private static final int WIDTH = 10; // Width of the board in blocks
    private static final int HEIGHT = 20; // Height of the board in blocks
    
    private Rectangle[][] grid = new Rectangle[WIDTH][HEIGHT];
    
	@Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rect.setFill(Color.LIGHTGRAY);
                rect.setStroke(Color.BLACK);
                grid[x][y] = rect;
                root.add(rect, x, y);
            }
        }

        Scene scene = new Scene(root, WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE);
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
