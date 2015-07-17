import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Brute {

	public static void main(String[] args) {
		
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
		
        //read input on file and input size
		In input = new In(args[0]);
		int size = input.readInt();
		
		//reads all the points and draws them
		Point[] num = new Point[size];
		for (int i = 0; i < num.length; i++) {
			num[i] = new Point(input.readInt(), input.readInt());
		}
		for (Point n: num) {
			n.draw();
		}
		
		Set<Set<Point>> sets = new HashSet<Set<Point>>();
		double slope = 0;
		
		/* Goes through each pair of points, finds the slope of the pair, 
		 * and adds all the points which lie on that line to a set.
		 * Each set with 4 or more points are added to the bigger set*/
		for (Point w: num) {
			for (Point x: num) {
				if (x == w) continue;
				slope = x.slopeTo(w);
				Set<Point> temp = new HashSet<Point>();
				temp.add(w);
				temp.add(x);
				for (Point y: num) {
					if (y == x || y ==w) continue;
					if (y.slopeTo(x) != slope) continue;
					temp.add(y);
				}
				if (temp.size() >= 4) sets.add(temp);
			}
		}
		
		//collects in a set all the permutations of lines made using only 4 points
		Set<Set<Point>> sets2 = new HashSet<Set<Point>>();
		for (Set<Point> n: sets) {
			Point[] line = n.toArray(new Point[n.size()]);
			Arrays.sort(line);
			for (Point x: line) {
				for (Point y: line) {
					if (y == x) continue;
					for (Point z: line) {
						if (z == y || z == x) continue;
						for (Point a: line) {
							if (a == x || a == y || a == z) continue;
							Set<Point> points = new HashSet<Point>();
							points.add(x); points.add(y); points.add(z); points.add(a);
							sets2.add(points);
						}
					}
				}
			}
		}
		
		//draws the lines
		for (Set<Point> x: sets2) {
			Point[] line = x.toArray(new Point[x.size()]);
			Arrays.sort(line);
			line[0].drawTo(line[line.length - 1]);
			for (int i = 0; i < line.length - 1; i++) {
				StdOut.print(line[i] + " -> ");
			}
			StdOut.println(line[line.length - 1]);
		}
		StdDraw.show(0);
		StdDraw.setPenRadius();
	}

}
