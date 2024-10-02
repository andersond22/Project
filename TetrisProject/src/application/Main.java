package application;
	
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	 private static final int BLOCK_SIZE = 30; // Size of each block
	    private static final int WIDTH = 10; // Width of the board in blocks
	    private static final int HEIGHT = 20; // Height of the board in blocks
	    private static Rectangle[][] grid = new Rectangle[WIDTH][HEIGHT];
	    private static int[][] grid2 = new int[WIDTH][HEIGHT];
	    private Piece randomPiece = new Piece();
	    private static Shape currentPiece;
	    private Text scoreText;
	    private static int score = 0;
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
		
		 scoreText = new Text("Score: " + score);
		 scoreText.setFill(Color.BLACK);
		 Font font = Font.font("Arial", FontWeight.BOLD, 20);
		 scoreText.setFont(font);
		 root.add(scoreText, 0, HEIGHT + 1, WIDTH, 1); 
		 startGame();
		 Scene scene = new Scene(root, WIDTH * BLOCK_SIZE + 10, HEIGHT * BLOCK_SIZE + 120);
		
		 
		 scene.setOnKeyPressed(event -> {
	            switch (event.getCode()) {
	                case LEFT:
	                    movePiece(-1, 0);
	                    break;
	                case RIGHT:
	                    movePiece(1, 0);
	                    break;
	                case DOWN:
	                    movePiece(0, 1);
	                    break;
	                case UP:
	                    rotatePiece();
	                    break;
				default:
					break;
	            }
	            drawPiece(currentPiece); 
	        });
		 
		 
		 
		 
		 
		
	        primaryStage.setTitle("Tetris");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	       
	}
	
	private void drawPiece(Shape piece) {
		 if (overlapsExistingBlocks(currentPiece.getShape())) {
		        System.exit(0);
		 }
        int[][] shape = piece.getShape();
        Color color = piece.getColor();
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] == 1) {
                    int boardX = piece.getX() + x;
                    int boardY = piece.getY() + y;
                    if (boardX >= 0 && boardX < WIDTH && boardY >= 0 && boardY < HEIGHT) {
                        grid[boardX][boardY].setFill(color);
                    }
                }
            }
        }
    }
	private boolean overlapsExistingBlocks(int[][] shape) {
	    for (int i = 0; i < shape.length; i++) {
	        for (int j = 0; j < shape[i].length; j++) {
	            if (shape[i][j] != 0) {
	                int gridX = currentPiece.getX() + j;
	                int gridY = currentPiece.getY() + i;

	                if (gridX >= 0 && gridX < WIDTH && gridY >= 0 && gridY < HEIGHT) {
	                    if (grid2[gridX][gridY] != 0) {
	                        return true; 
	                    }
	                }
	            }
	        }
	    }
	    return false;
	}
	private boolean leftCollision(int[][] shape,int x, int y, int[][] grid) {
		 for (int i = 0; i < shape.length; i++) {
		        for (int j = 0; j < shape[0].length; j++) {
		        	if (shape[i][j] != 0) {
		                if (x + j < 0) {
		                    return true;
		                }
		                if (y + i >= 0 && y + i < grid.length && x + j - 1 >= 0) {
		              
		                    if (grid[y + i][x + j - 1] != 0) {
		                        return true;
		                    }
		                }
		                }		        
		        }
		}
		 return false;
	}
	private boolean rightCollision(int[][] shape, int x, int y, int[][] grid) {
		for (int i = 0; i < shape.length; i++) {
	        for (int j = 0; j < shape[0].length; j++) {
	            if (shape[i][j] != 0) {
	               
	                if (x + j >= WIDTH) {
	                    return true;
	                }
	                if (y + i >= 0 && y + i < grid.length && x + j + 1 < grid[0].length) {
	                    if (grid[y + i][x + j + 1] != 0) {
	                        return true;
	                    }
	                    
	                }
	               
	               
	            }
	        }
	    }
	    return false;
	}
	private boolean floorCollision(int[][] shape,int[][] grid, int height) {
		if(height >= 19) {
				return true;
		}
		for (int i = 0; i < shape.length; i++) {
	        for (int j = 0; j < shape[i].length; j++) {
	            if (shape[i][j] != 0) { 
	                int gridX = currentPiece.getX() + j;
	                int gridY = currentPiece.getY() + i + 1; 
	                if (gridX >= 0 && gridX < WIDTH && gridY >= 0 && gridY < HEIGHT) {
	                	if (grid[gridX][gridY] != 0) { 
	                        return true;
	                }
	            }
	        }
		}
		}
		return false;
	}
	private boolean ceilingCollision(int[][] shape, int[][] grid) {
		 for (int i = 0; i < shape.length; i++) {
		        for (int j = 0; j < shape[i].length; j++) {
		            if (shape[i][j] != 0) {
		                int gridY = currentPiece.getY() + i;
		                if (gridY < 0) {
		                
		                    return true;
		                }
		            }
		        }
		    }
		    return false;
		}
	private void movePiece(int x, int y) {
		   
	    int newX = currentPiece.getX() + x;
	    int newY = currentPiece.getY() + y;
	    if (isValidMove(currentPiece.getShape(), newX, newY, grid2)) {
	        currentPiece.setX(newX);
	        currentPiece.setY(newY);
	        clearGrid();
	        drawPiece(currentPiece);
	    } else if (y == 1) { 
	        lockPiece(currentPiece.getShape());
	        currentPiece = new Shape(randomPiece.getShape(), randomPiece.getColor());
	    }
	}
	private boolean isValidMove(int[][] shape, int x, int y, int[][] grid) {
	    for (int i = 0; i < shape.length; i++) {
	        for (int j = 0; j < shape[i].length; j++) {
	            if (shape[i][j] != 0) {
	                int gridX = x + j;
	                int gridY = y + i;
	                
	                if (gridX < 0 || gridX >= WIDTH || gridY >= HEIGHT) {
	                    return false;
	                }
	                
	                if (gridY >= 0 && grid[gridX][gridY] != 0) {
	                    return false; 
	                }
	            }
	        }
	    }
	    return true;
	}
	private void clearGrid() {
	    for (int x = 0; x < WIDTH; x++) {
	        for (int y = 0; y < HEIGHT; y++) {
	        	if(grid2[x][y] == 0)
	            grid[x][y].setFill(Color.LIGHTGRAY);
	        }
	        }
	    }
	
	 private void rotatePiece() {
	        currentPiece.rotate();
	        clearGrid();
	        drawPiece(currentPiece);
	       
	 }
	 private void lockPiece(int[][] shape) {
		 int pieceY = currentPiece.getY();
		 int pieceX = currentPiece.getX();

		    for (int i = 0; i < shape.length; i++) {
		        for (int j = 0; j < shape[i].length; j++) {
		            if (shape[i][j] != 0) {
		                int gridY = pieceY + i;
		                int gridX = pieceX + j;

		                
		                if (gridY >= 0 && gridY < HEIGHT && gridX >= 0 && gridX < WIDTH) {
		                    grid2[gridX][gridY] = shape[i][j]; 
		                }
		            }
		        }
		    }
		    checkFullRows();
		}
	 private void checkFullRows() {
		    for (int y = HEIGHT - 1; y >= 0; y--) { 
		        boolean isFull = true;
		        for (int x = 0; x < WIDTH; x++) {
		            if (grid2[x][y] == 0) {
		                isFull = false;
		                break;
		            }
		        }

		        if (isFull) {
		            clearRow(y);
		            shiftRowsDown(y);
		            score += 100;
		            scoreText.setText("Score: " + score);
		            y++; 
		        }
		    }
		}
	 private void clearRow(int row) {
		    for (int x = 0; x < WIDTH; x++) {
		        grid2[x][row] = 0; 
		    }
		}
	 private void shiftRowsDown(int startRow) {
		    for (int y = startRow - 1; y >= 0; y--) {
		        for (int x = 0; x < WIDTH; x++) {
		            grid2[x][y + 1] = grid2[x][y]; 
		        }
		    }
		   
		    for (int x = 0; x < WIDTH; x++) {
		        grid2[x][0] = 0;
		    }
		}
	private void startGame() {
			 currentPiece = new Shape(randomPiece.getShape(), randomPiece.getColor());
			 drawPiece(currentPiece);
			 Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> { movePiece(0, 1); 
			 }));
			 timeline.setCycleCount(Timeline.INDEFINITE);
			 timeline.play();
			 Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(1), b -> {
			 int[][] a = currentPiece.getShape();
			 int height = currentPiece.getY() + a.length - 1;
			 
			
			 if(floorCollision(currentPiece.getShape(), grid2, height)) {
				  lockPiece(currentPiece.getShape());
				  currentPiece = new Shape(randomPiece.getShape(), randomPiece.getColor());
				  clearGrid();
			      drawPiece(currentPiece);
			 }
			 if(leftCollision(currentPiece.getShape(), currentPiece.getX(), currentPiece.getY(), grid2)) {
				 currentPiece.setX(currentPiece.getX() + 1);
				 clearGrid();
		         drawPiece(currentPiece);     	
			 }
			 if(rightCollision(currentPiece.getShape(), currentPiece.getX(), currentPiece.getY(), grid2)) {
				 currentPiece.setX(currentPiece.getX() - 1);
				 clearGrid();
		         drawPiece(currentPiece);     	
			 }
			 if(ceilingCollision(currentPiece.getShape(), grid2)){
				
				 System.exit(0);
				
			 }
			 }));
			 timeline2.setCycleCount(Timeline.INDEFINITE);
			 timeline2.play();
	}
			        
			
		
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
