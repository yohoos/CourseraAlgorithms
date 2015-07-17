import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] queue;
	private int N; //length of array

	public RandomizedQueue() {
		this.queue = (Item[]) new Object[2];
		this.N = 0;
	}
	
	public boolean isEmpty() {
		return this.N == 0;
	}
	
	public int size() {
		return this.N;
	}
	
	public void enqueue(Item item) {
		if (item.equals(null)) {
			throw new NullPointerException();
		}
		
		this.queue[N++] = item;
		
		//resize array
		if (this.N == this.queue.length) {
			resize(this.N*2);
		}
	}
	
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		//dequeues random array index by exchanging with last array index
		int num = StdRandom.uniform(this.N);
		Item item = this.queue[num];
		this.queue[num] = this.queue[--this.N];
		
		//avoids loitering and resizes
		this.queue[this.N] = null; 
		if (this.N > 0 && this.N == this.queue.length/4) {
			resize(this.queue.length/2);
		}
		
		return item;
	}
	
	
	//returns random array element without deletion
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return this.queue[StdRandom.uniform(N)];
	}
	
	
	//array resizing
	private void resize(int max) {
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0; i < this.N; i++) {
			temp[i] = this.queue[i];
		}
		this.queue = temp;
	}
	
	
	//implements interator for this data type
	@Override
	public Iterator<Item> iterator() {
		return new ArrayIterator();
	}
	
	private class ArrayIterator implements Iterator<Item> {
		
		private int max = N;
		private Item[] array = queue.clone();
		
		public ArrayIterator() {
			
		}
		
		@Override
		public boolean hasNext() { 
			return this.max > 0;
		}
		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			int num = StdRandom.uniform(this.max);
			Item item = this.array[num];
			this.array[num] = this.array[--this.max];
			this.array[max] = null;
			return item;
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
