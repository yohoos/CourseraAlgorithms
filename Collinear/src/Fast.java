import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Fast {
	
	public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
        //read input from file and input size
        In input = new In(args[0]);
		int size = input.readInt();
		
		//reads input into an array and draws each point
		Point[] num = new Point[size];
		for (int i = 0; i < num.length; i++) {
			num[i] = new Point(input.readInt(), input.readInt());
		}
		for (Point n: num) {
			n.draw();
		}

		Set<Set<Point>> sets = new HashSet<Set<Point>>();
		Point[] numCopy = num.clone();
		for (Point x: num) {
			
			//calculates and collects all the possible slopes with point x in an array
			ArrayList<Double> slopes = new ArrayList<Double>();
			for (Point y: num) {
				slopes.add(y.slopeTo(x));
			}
			
			/* Sorts the slopes so that repeat slopes are placed together
			 * and sorts the points according to the slopes they make with x.
			 * This makes it so that the array indices of the point array point
			 * to the slope it makes with point x.
			 */
			Double[] sort = slopes.toArray(new Double[slopes.size()]);
			Arrays.sort(sort);
			Arrays.sort(numCopy, x.SLOPE_ORDER);

			double prevSlope = 0;
			int startIndex = 0;
			int count = 1;

			for (int i = 0; i <= sort.length; i++) {
				
				// special condition case for when the desired slope repeats occur at the end of the array
				if (i == sort.length) {
					if (count >= 3) {
						Set<Point> set = new HashSet<Point>();
						set.add(x);
						for (int j = startIndex; j < i; j++) {
							set.add(numCopy[j]);
						}
						sets.add(set);
					}
					break;
				}
				
				// finds slope repeats of 3 or more and adds their corresponding points to a set
				if (!sort[i].equals(prevSlope)) {
					if (count >= 3) {
						Set<Point> set = new HashSet<Point>();
						set.add(x);
						for (int j = startIndex; j < i; j++) {
							set.add(numCopy[j]);
						}
						sets.add(set);
					}
					prevSlope = sort[i];
					startIndex = i;
					count = 1;
				} else if (sort[i].equals(prevSlope)) {
					count++;
				}
			}
		}
		
		//draws each of the lines from one extremity to the other
		for (Set<Point> set: sets) {
			Point[] line = set.toArray(new Point[set.size()]);
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
