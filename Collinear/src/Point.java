 import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	if (that.y == this.y && that.x == this.x) return Double.NEGATIVE_INFINITY;
        else if ((that.x - this.x) == 0) return Double.POSITIVE_INFINITY;
        else if ((that.y - this.y) == 0) return +0;
        else return (double) (that.y - this.y) / (that.x - this.x);
    }

    // comparing y-coordinates and breaking ties by x-coordinates
    @Override
	public int compareTo(Point that) {
        if (this.y == that.y) {
        	if (this.x == that.x) return 0;
        	else if (this.x > that.x) return 1;
        	else return -1;
        }
        else if(this.y > that.y) return 1;
        else return -1;
    }

    // return string representation of this point
    @Override
	public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class SlopeOrder implements Comparator<Point> {
    	
    	@Override
		public int compare(Point m, Point n) {
    		double mslope = slopeTo(m);
    		double nslope = slopeTo(n);
    		if ((mslope - nslope) > 0) {
        		return (int) Math.ceil((mslope - nslope));
    		}
    		else {
    			return (int) Math.floor((mslope - nslope));
    		}
    	}
    	
    }

    public static void main(String[] args) {
    	
    }
}
