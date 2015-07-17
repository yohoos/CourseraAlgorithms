
public class Subset {

	public static void main(String[] args) {
		
		
		//reads in values for a randomized queue and then outputs the values
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			queue.enqueue(StdIn.readString());
		}
		while (k > 0) {
			System.out.println(queue.dequeue());
			k--;
		}
	}

}
