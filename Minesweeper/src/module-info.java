/**
 * 
 */
/**
 * 
 */
module Minesweeper {
	import java.util.Random;
	import java.util.Scanner;

	public class MineSweeper {
	    private int[][] mineField;
	    private boolean[][] revealed;
	    private int rowCount;
	    private int colCount;
	    private int mineCount;

	    public MineSweeper(int rowCount, int colCount) {
	        this.rowCount = rowCount;
	        this.colCount = colCount;
	        this.mineField = new int[rowCount][colCount];
	        this.revealed = new boolean[rowCount][colCount];
	        this.mineCount = rowCount * colCount / 4;

	        initializeMineField();
	        placeMines();
	    }

	    private void initializeMineField() {
	        for (int i = 0; i < rowCount; i++) {
	            for (int j = 0; j < colCount; j++) {
	                mineField[i][j] = 0;
	                revealed[i][j] = false;
	            }
	        }
	    }

	    private void placeMines() {
	        Random random = new Random();

	        for (int i = 0; i < mineCount; i++) {
	            int row, col;
	            do {
	                row = random.nextInt(rowCount);
	                col = random.nextInt(colCount);
	            } while (mineField[row][col] == -1);

	            mineField[row][col] = -1;
	        }
	    }

	    private void printBoard(boolean revealMines) {
	        System.out.print("  ");
	        for (int i = 0; i < colCount; i++) {
	            System.out.print(i + " ");
	        }
	        System.out.println();

	        for (int i = 0; i < rowCount; i++) {
	            System.out.print(i + " ");
	            for (int j = 0; j < colCount; j++) {
	                if (revealed[i][j] || revealMines) {
	                    if (mineField[i][j] == -1) {
	                        System.out.print("* ");
	                    } else {
	                        System.out.print(mineField[i][j] + " ");
	                    }
	                } else {
	                    System.out.print(". ");
	                }
	            }
	            System.out.println();
	        }
	    }

	    private boolean isValidMove(int row, int col) {
	        return row >= 0 && row < rowCount && col >= 0 && col < colCount && !revealed[row][col];
	    }

	    private void revealCell(int row, int col) {
	        if (isValidMove(row, col)) {
	            revealed[row][col] = true;
	            if (mineField[row][col] == 0) {
	                revealSurroundingCells(row, col);
	            }
	        }
	    }

	    private void revealSurroundingCells(int row, int col) {
	        for (int i = row - 1; i <= row + 1; i++) {
	            for (int j = col - 1; j <= col + 1; j++) {
	                revealCell(i, j);
	            }
	        }
	    }

	    private boolean isGameWon() {
	        for (int i = 0; i < rowCount; i++) {
	            for (int j = 0; j < colCount; j++) {
	                if (!revealed[i][j] && mineField[i][j] != -1) {
	                    return false;
	                }
	            }
	        }
	        return true;
	    }

	    public void play() {
	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            printBoard(false);

	            System.out.print("Enter row and column (separated by a space): ");
	            int row = scanner.nextInt();
	            int col = scanner.nextInt();

	            if (!isValidMove(row, col)) {
	                System.out.println("Invalid move. Try again.");
	                continue;
	            }

	            if (mineField[row][col] == -1) {
	                printBoard(true);
	                System.out.println("Game over! You hit a mine.");
	                break;
	            } else {
	                revealCell(row, col);

	                if (isGameWon()) {
	                    printBoard(true);
	                    System.out.println("Congratulations! You won!");
	                    break;
	                }
	            }
	        }

	        scanner.close();
	    }

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("Enter the number of rows: ");
	        int rows = scanner.nextInt();

	        System.out.print("Enter the number of columns: ");
	        int cols = scanner.nextInt();

	        MineSweeper game = new MineSweeper(rows, cols);
	        game.play();

	        scanner.close();
	    }
	}
}