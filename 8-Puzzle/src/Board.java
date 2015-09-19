import java.util.ArrayList;
import java.util.Arrays;


public class Board {

	private int size;
	private int[][] grid;
	private int xBlank;
	private int yBlank;
	
	public Board(int[][] blocks) {
		this.size = blocks.length;
		this.grid = new int[this.size][this.size];
		
		//find the blank space and fill in new board copy
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (blocks[i][j] == 0) {
					this.xBlank = i;
					this.yBlank = j;
				}
				this.grid[i][j] = blocks[i][j];
			}
		}
	}

	public int dimension() {
		return this.size;
	}
	
	public int hamming() {
		
		//determines how many grid positions have the wrong value
		int count = 0;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (i == this.size - 1 && j == this.size - 1) {
					continue;
				}else if (this.grid[i][j] != ((i * this.size) + (j + 1))) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int manhattan() {
		
		//determines how far each value is from its correct position and adds up the distances
		int count = 0;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.grid[i][j] == 0) {
					continue;
				} else {
					int diff = Math.abs(j - (this.grid[i][j] - 1) % this.size);
					int horizontal = diff % this.size;
					int vertical = Math.abs(i - (this.grid[i][j] - 1) / this.size);
					count += vertical + horizontal;
				}
			}
		}
		return count;
	}
	
	public boolean isGoal() {
		return this.hamming() == 0;
	}
	
	public Board twin() {
		
		//creates second copy with a single pair of adjacent values swapped
		int[][] copyGrid = cloneBoard(this.grid);
		int x = 0;
		int y = 0;
		if (copyGrid[x][y] == 0 || copyGrid[x][y + 1] == 0) {
			x++;
		}
		int temp = copyGrid[x][y];
		copyGrid[x][y] = copyGrid[x][y + 1];
		copyGrid[x][y + 1] = temp;
		return new Board(copyGrid);
	}
	
	public boolean equals(Object y) {
		if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        if (y == this)
            return true;
        
        //determines equality
        Board yBoard = (Board) y;
        return Arrays.deepEquals(this.grid, yBoard.grid);
	}
	
	private int[][] cloneBoard(int[][] blocks) {
		
		//makes new copy of a board
		int[][] copyGrid = new int[this.size][this.size];
		for(int i = 0; i < this.size; i++) {
			  for(int j = 0; j < this.size; j++) {
				    copyGrid[i][j] = this.grid[i][j];
			  }
		}
		return copyGrid;
	}
	
	public Iterable<Board> neighbors() {
		ArrayList<Board> list = new ArrayList<Board>();
		int x = this.xBlank;
		int y = this.yBlank;
		
		//find all neighbors with respect to the movement of the blank space
		if (x > 0) {
			int[][] copyGrid = cloneBoard(this.grid);
			int temp = copyGrid[x][y];
			copyGrid[x][y] = copyGrid[x - 1][y];
			copyGrid[x - 1][y] = temp;
			list.add(new Board(copyGrid));
		}
		if (x < this.size - 1) {
			int[][] copyGrid = cloneBoard(this.grid);
			int temp = copyGrid[x][y];
			copyGrid[x][y] = copyGrid[x + 1][y];
			copyGrid[x + 1][y] = temp;
			list.add(new Board(copyGrid));
		}
		if (y > 0) {
			int[][] copyGrid = cloneBoard(this.grid);
			int temp = copyGrid[x][y];
			copyGrid[x][y] = copyGrid[x][y - 1];
			copyGrid[x][y - 1] = temp;
			list.add(new Board(copyGrid));
		}
		if (y < this.size - 1) {
			int[][] copyGrid = cloneBoard(this.grid);
			int temp = copyGrid[x][y];
			copyGrid[x][y] = copyGrid[x][y + 1];
			copyGrid[x][y + 1] = temp;
			list.add(new Board(copyGrid));
		}
		return list;
	}
	
	public String toString() {
		StringBuffer obj = new StringBuffer("");
		obj.append(this.size);
		obj.append("\n");
		for (int[] x: this.grid) {
			for (int y: x) {
				obj.append(String.format("%2d ", y));
			}
			obj.append("\n");
		}
		return obj.toString();
	}

	public static void main(String[] args) {
		int count = 0;
		int[][] test = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == 2 && j == 2) {
					test[i][j] = 0;
				} else test[i][j] = ++count;			
			}
		}
		test[1][0] = 5;
		test[1][1] = 0;
		test[2][0] = 4;
		test[2][2] = 7;
		Board tester = new Board(test);
		System.out.println(tester);
		for (Board x: tester.neighbors()) {
			System.out.println(x);
		}
	}

}
