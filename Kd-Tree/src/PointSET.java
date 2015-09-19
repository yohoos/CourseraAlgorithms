import java.util.ArrayList;
import java.util.TreeSet;


public class PointSET {

	private TreeSet<Point2D> set;
	
	public PointSET() {
		this.set = new TreeSet<Point2D>();
	}
	
	public boolean isEmpty() {
		return this.set.isEmpty();
	}
	
	public int size() {
		return this.set.size();
	}
	
	public void insert(Point2D p) {
		if (p == null) {
			throw new NullPointerException();
		}
		this.set.add(p);
	}
	
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new NullPointerException();
		}
		return this.set.contains(p);
	}
	
	public void draw() {
		for (Point2D p: this.set) {
			p.draw();
		}
	}
	
	public Iterable<Point2D> range (RectHV rect) {
		if (rect == null) {
			throw new NullPointerException();
		}
		ArrayList<Point2D> list = new ArrayList<Point2D>();
		for (Point2D p: this.set) {
			if (rect.contains(p)) {
				list.add(p);
			}
		}
		return list;
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new NullPointerException();
		}
		Point2D nearest = null;
		double distance = Double.MAX_VALUE;
		for (Point2D query: this.set) {
			double testDist = query.distanceTo(p);
			if (testDist < distance) {
				distance = testDist;
				nearest = query;
			}
		}
		return nearest;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
