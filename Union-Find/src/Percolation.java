import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Percolation {
	private boolean[] idGrid;
	private boolean[] bottom;
	private WeightedQuickUnionUF unionTest;
	private int gridLength;
	
	public Percolation(int N){
		if(N<=0){
			throw new IllegalArgumentException("Create a larger grid!");
		}
		
		//create 2 arrays one detail openness and the other to detail connectedness to the bottom row of indices
		//the idGrid emulates a 2D grid through a mathematical relationship
		this.idGrid = new boolean[N*N+1]; 
		this.bottom = new boolean[N*N+1]; 
		this.gridLength = N;
		
		//create new QuickUnion object to hold 1D indices
		unionTest = new WeightedQuickUnionUF(N*N+1);
		
		//create virtual top node
		for(int i=1;i<=N;i++){
			unionTest.union(0, i);
		}
		
		//indicates in the array that the bottom rows are connected to themselves
		for(int i=N*N;i>N*N-N;i--){
			this.bottom[i] = true;
		}
	}
	
	public void open(int i, int j){ // open site (row i, column j) if it is not open already
		if(i<=0 || i>this.gridLength || j<=0 || j>this.gridLength){
			throw new IndexOutOfBoundsException();
		}
		int N = this.gridLength;
		
		/*opens argument site is previously unopened and also connects to neighboring open sites
		while also updating its connectedness to the bottom row indices*/
		if(this.idGrid[(i-1)*N + j] == false){
			this.idGrid[(i-1)*N + j] = true;
			boolean connectBottom = this.bottom[(i-1)*N + j];
			if(i-1>0){
				if(idGrid[(i-2)*N + j] == true){
					if(this.bottom[this.unionTest.find((i - 2)*N + j)] == true){
						connectBottom = true;
					}
					unionTest.union((i-1)*N + j, (i-2)*N + j);
				}
			}
			if(i+1<=this.gridLength){
				if(idGrid[(i)*N + j] == true){
					if(this.bottom[this.unionTest.find((i)*N + j)] == true){
						connectBottom = true;
					}
					unionTest.union((i-1)*N + j, (i)*N + j);
				}
			}
			if(j-1>0){
				if(idGrid[(i-1)*N + j - 1] == true){
					if(this.bottom[(this.unionTest.find((i-1)*N + j - 1))] == true){
						connectBottom = true;
					}
					unionTest.union((i-1)*N + j, (i-1)*N + j-1);
				}
			}
			if(j+1<=this.gridLength){
				if(idGrid[(i-1)*N + j + 1] == true){
					if(this.bottom[this.unionTest.find((i-1)*N + j + 1)] == true){
						connectBottom = true;
					}
					unionTest.union((i-1)*N + j, (i-1)*N + j+1);
				}
			}
			//update root status
			if(this.bottom[this.unionTest.find((i-1)*N + j)] != true){
				this.bottom[this.unionTest.find((i-1)*N + j)] = connectBottom;
			}
		}
	}
    public boolean isOpen(int i, int j){ // is site (row i, column j) open?
    	if(i<=0 || i>this.gridLength || j<=0 || j>this.gridLength){
			throw new IndexOutOfBoundsException();
		}
    	return this.idGrid[(i-1)*this.gridLength + j] == true;
    }
    
    public boolean isFull(int i, int j){ // is site (row i, column j) full?
    	if(i<=0 || i>this.gridLength || j<=0 || j>this.gridLength){
			throw new IndexOutOfBoundsException();
		}
    	if(isOpen(i, j)){
    		return unionTest.connected(0, (i-1)*this.gridLength + j);
    	}
    	return false;
    }
    public boolean percolates(){  // does the system percolate?
    	if(this.gridLength == 1){
    		return isOpen(1,1);
    	}
    	return (this.bottom[unionTest.find(0)]);
    }

    public static void main(String[] args) throws FileNotFoundException{ // test client
    	Scanner test = new Scanner(new File("insert test file"));
    	int size = test.nextInt();
    	Percolation stuff = new Percolation(size);
    	while(test.hasNextInt()){
    		int i = test.nextInt();
    		int j = test.nextInt();
    		stuff.open(i, j);
    	}
    	test.close();
    	System.out.println(stuff.unionTest.find(1));
    	System.out.print(stuff.percolates());
	}
}
