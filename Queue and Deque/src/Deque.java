import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

	private Node first;
	private Node last;
	private int N; //length of linked list
	
	public Deque() {
	}
	
	private class Node {
		Item item;
		Node next;
		Node previous;
	}
	
	public boolean isEmpty() { 
		return this.N == 0; 
	}
	
	public int size() { 
		return this.N; 
	}
	
	//adds item to start of the list
	public void addFirst(Item item) {
		if(item.equals(null)){
			throw new NullPointerException("Can't Add Null Item!");
		}
		
		//creates new node for Item
		Node oldFirst = this.first;
		this.first = new Node();
		this.first.item = item;
		
		//connects new node to old first node
		if (isEmpty()) this.last = this.first;
		else {
			this.first.next = oldFirst;
			this.first.previous = null; 
			oldFirst.previous = this.first;
		}
		
		this.N++;
	}
	
	//adds item to end of the list
	public void addLast(Item item) {
		if(item.equals(null)){
			throw new NullPointerException("Can't Add Null Item!");
		}
		
		//creates new last node
		Node oldlast = this.last;
		this.last = new Node();
		this.last.item = item;
		
		//connects new last to old last
		if (isEmpty()) this.first = this.last;
		else {
			this.last.next = null;
			this.last.previous = oldlast;
			oldlast.next = this.last;
		}
		this.N++;
	}
	
	// Remove item from the beginning of the list.
	public Item removeFirst() { 
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		//deletion
		Item item = this.first.item;
		this.first = this.first.next;
		this.N--;
		
		//updates last if list is now empty
		if (isEmpty()) this.last = null;
		else this.first.previous = null;
		return item;
	}
	
	public Item removeLast() { // Remove item from the end of the list.
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		//deletion
		Item item = this.last.item;
		this.last = this.last.previous;
		this.N--;
		
		//updates first if list is now empty
		if (isEmpty()) this.first = null;
		else this.last.next = null;
		return item;
	}
	
	@Override
	//creates new iterator
	public Iterator<Item> iterator() {
		return new QueueIterator();
	}
	
	private class QueueIterator implements Iterator<Item> {
		private Node current = first;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Don't Remove!");
		}
		
		@Override
		public Item next() {
			if (hasNext() == false) throw new NoSuchElementException();
			Item item = this.current.item;
			this.current = current.next;
			return item;
		}
		
	}
	
	public static void main(String[] args){

	}

}
