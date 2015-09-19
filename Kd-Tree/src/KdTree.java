
public class KdTree {
	
	private Node root;

	public KdTree() {
		root = null;
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	public int size() {
		return size(this.root);
	}
	
	private int size(Node root) {
		if (root == null) {
			return 0;
		} else {
			return root.size;
		}
	}
	
	public void insert(Point2D p) {
		this.root = insert(this.root, p, true, 0, 0, 1, 1);
	}
	
	private Node insert(Node cRoot, Point2D p, boolean orientation, double xMin, double yMin, double xMax, double yMax) {
		if (cRoot == null) return new Node(p, new RectHV(xMin, yMin, xMax, yMax) ,1);
		
		//checks to see if we already have this point, if we do we return the point and skip the comparisons
		if (cRoot.point.equals(p)) return cRoot;
		
		//choosing correct comparison methods
		if (orientation == true) {
			//x-order comparison
			double compare = p.x() - cRoot.point.x();
			
			if (compare < 0) cRoot.left = insert(cRoot.left, p, !orientation, xMin, yMin, cRoot.point.x(), yMax);
			else cRoot.right = insert(cRoot.right, p, !orientation, cRoot.point.x(), yMin, xMax, yMax);

		} else {
			//uses y-order comparison
			double compare = p.y() - cRoot.point.y();
			
			if (compare < 0) cRoot.left = insert(cRoot.left, p, !orientation, xMin, yMin, xMax, cRoot.point.y());
			else cRoot.right = insert(cRoot.right, p, !orientation, xMin, cRoot.point.y(), xMax, yMax);	
		}
		
		cRoot.size = size(cRoot.left) + size(cRoot.right) + 1;
		return cRoot;
	}
	
	public boolean contains(Point2D p) {
		return contains(this.root, p, true);
	}
	
	private boolean contains(Node cRoot, Point2D p, boolean orientation) {
		if (cRoot == null) return false;
		
		//makes it faster to check for equality first
		if (cRoot.point.equals(p)) return true;
		
		//choosing correct comparators
		double compare;
		if (orientation == true) {
			compare = p.x() - cRoot.point.x();
		} else {
			compare = p.y() - cRoot.point.y();
		}
		
		if (compare < 0) {
			return contains(cRoot.left, p, !orientation);
		} else {
			return contains(cRoot.right, p, !orientation);
		}
	}
	
	public void draw() {
		//recursively draw each point using StdDraw.line to the edges of the 1.0 by 1.0 box
		draw(this.root, true);
	}
	
	private void draw(Node cRoot, boolean orientation) {
		if (cRoot == null) {
			return;
		}
		
		if (orientation == true) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(cRoot.point.x(), cRoot.rect.ymin(), cRoot.point.x(), cRoot.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(cRoot.rect.xmin(), cRoot.point.y(), cRoot.rect.xmax(), cRoot.point.y());
		}
		
		if (cRoot.left != null) {
            draw(cRoot.left, !orientation);
        }

        if (cRoot.right != null) {
            draw(cRoot.right, !orientation);
        }
		
		//draw point last to be on top of line
		StdDraw.setPenColor(StdDraw.BLACK);
		cRoot.point.draw();
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> list = new Queue<Point2D>();
		range(this.root, rect, list);
		return list;
	}
	
	private void range(Node cRoot, RectHV rect, Queue<Point2D> list) {
		if (cRoot == null) {
			return;
		}
		
		//does not search further if rectangles do not intersect
		if (rect.intersects(cRoot.rect)) {
			//if it intersects we check to see if the point is contained in the query rectangle
			if (rect.contains(cRoot.point)) {
				list.enqueue(cRoot.point);
			}		
			
			//recursively search the left and then right subtrees
			range(cRoot.left, rect, list);
			range(cRoot.right, rect, list);
		}
	}
	
	public Point2D nearest(Point2D p) {
		return nearest(this.root, p, Double.POSITIVE_INFINITY);
	}
	
	private Point2D nearest(Node cRoot, Point2D query, double distance) {
		if (cRoot == null) {
			return null;
		}
		
		//skips subtree if point distance to query is farther than current closest
		if (cRoot.rect.distanceTo(query) >= distance) {
			return null;
		}
		
		Point2D closest = null;
		double nearestDist = distance;
		double d;
		
		//if current node holds a point closer to the query, update info
		d = query.distanceTo(cRoot.point);
		if (d < nearestDist) {
			closest = cRoot.point;
			nearestDist = d;
		}
			
		Node first = cRoot.left;
		Node second = cRoot.right;
		
		//switches which subtree to go down first depending on which rectangle the query point is closer to
		if (first != null && second != null) {
			if (first.rect.distanceTo(query) > second.rect.distanceTo(query)) {
				first = cRoot.right;
				second = cRoot.left;
			}
		}
		
		Point2D firstPoint = nearest(first, query, nearestDist);
		if (firstPoint != null) {
			d = query.distanceTo(firstPoint);
			if (d < nearestDist) {
				closest = firstPoint;
				nearestDist = d;
			}
		}
		
		Point2D secondPoint = nearest(second, query, nearestDist);
		if (secondPoint != null) {
			d = query.distanceTo(secondPoint);
			if (d < nearestDist) {
				closest = secondPoint;
				nearestDist = d;
			}
		}
		
		return closest;
	}
	
	private class Node {
		
		private Point2D point;
		private RectHV rect;
		private int size;
		private Node left;
		private Node right;
		
		public Node(Point2D p, RectHV rect, int size) {
			this.point = p;
			this.size = size;
			this.rect = rect;
		}
	}

	public static void main(String[] args) {
		StdDraw.setCanvasSize();
		StdDraw.setXscale();
		StdDraw.setYscale();

	}
}
