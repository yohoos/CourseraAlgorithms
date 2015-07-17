
public class PercolationStats {
	private double[] results;
	
	public PercolationStats(int N, int T){
		if(N <= 0 || T <= 0){
			throw new IllegalArgumentException();
		}
		
		//constructs a list of T open to non-opened site ratios with grids of size N by N
		this.results = new double[T];
		for(int i=0;i<this.results.length;i++){
			Percolation testSubject = new Percolation(N);
			double counter = 0;
			while(!testSubject.percolates()){
	        	int random = StdRandom.uniform(1,N+1);
	        	int random2 = StdRandom.uniform(1,N+1);
				if(!testSubject.isOpen(random, random2)){
					testSubject.open(random, random2);
					counter++;
				}
			}
			this.results[i] = counter/(N*N);
		}
	}
	
	public double mean(){
		return StdStats.mean(this.results);
	}
	
	public double stddev(){
		return StdStats.stddev(this.results);
	}
	
	//95% confidence interval
	public double confidenceLo(){ 
		return mean() - 1.96*(stddev()/Math.sqrt(this.results.length));
	}
	
	//95% confidence interval
	public double confidenceHi(){
		return mean() + 1.96*(stddev()/Math.sqrt(this.results.length));
	}
	
	public static void main(String[] args){ //test client
		int gridSize = StdIn.readInt();
		int trials = StdIn.readInt();
		PercolationStats test = new PercolationStats(gridSize, trials);
		System.out.printf("mean                    = %f\n", test.mean());
		System.out.printf("stddev                  = %f\n", test.stddev());
		System.out.printf("95%% confidence interval = %.15f, %.15f\n", test.confidenceLo(), test.confidenceHi());
	}
}
