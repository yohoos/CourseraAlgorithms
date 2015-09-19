

public class Solver {
	private Node goal;

	public Solver(Board initial) {
		if (initial == null) {
			throw new NullPointerException();
		}
		
		//initial setup
		MinPQ<Node> pq = new MinPQ<Node>();
		MinPQ<Node> pq2 = new MinPQ<Node>();
		pq.insert(new Node(initial, 0, null));
		pq2.insert(new Node(initial.twin(), 0, null));
		Node min = pq.delMin();
		Node min2 = pq2.delMin();
		this.goal = min;
		
		//make completed pq using A* Search Algorithm
		while (min.getBoard().manhattan() != 0) {
			
			//original board
			Iterable<Board> neighbors = min.getBoard().neighbors();
			for (Board x: neighbors) {
				if (min.previous == null || !x.equals(min.previous.getBoard())) {
					pq.insert(new Node(x, min.getMoves() + 1, min));
				}
			}
			min = pq.delMin();
			this.goal = min;
			
			//twin board
			Iterable<Board> neighbors2 = min2.getBoard().neighbors();
			for (Board x: neighbors2) {
				if (min2.previous == null || !x.equals(min2.previous.getBoard())) {
					pq2.insert(new Node(x, min2.getMoves() + 1, min2));
				}
			}
			min2 = pq2.delMin();
			
			//ends loop if twin board gets to the goal first
			if (min2.getBoard().manhattan() == 0) {
				this.goal = null;
				break;
			}
		}
	}
	
	private class Node implements Comparable<Node> {
		private Board board;
		private Node previous;
		private int priority;
		private int moves;

		public Node(Board board, int moves, Node previous) {
			this.board = board;
			this.priority = board.manhattan() + moves;
			this.previous = previous;
			
			//needs an instance variable to save current number of steps
			this.moves = moves; 
		}
		
		public int getMoves() {
			return moves;
		}

		public Node getPrevious() {
			return previous;
		}
		
		public int getPriority() {
			return priority;
		}
		
		public Board getBoard() {
			return this.board;
		}
		
		public int compareTo(Node x) {
			if (this.priority == x.getPriority()) {
				return 0;
			}
			else if (this.priority > x.getPriority()) return 1;
			else return -1;
		}
	}
	
	public boolean isSolvable() {
		return this.goal != null;
	}
	
	public int moves() {
		if (isSolvable()) return this.goal.moves;
		else return -1;
	}
	
	public Iterable<Board> solution() {
		
		//obtains solution by getting the parents of each board starting with the goal
		if (isSolvable()) {
			Stack<Board> stack = new Stack<Board>();
			Node loop = this.goal;
			while (loop.getPrevious() != null) {
				stack.push(loop.getBoard());
				loop = loop.getPrevious();
			}
			stack.push(loop.getBoard());
			loop = loop.getPrevious();
			return stack;
		}
		else return null;
	}

	public static void main(String[] args) {

		 // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }

	}

}
