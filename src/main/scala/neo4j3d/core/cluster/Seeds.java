package neo4j3d.core.cluster;

import java.util.LinkedList;
import java.util.List;

import neo4j3d.core.BBox;
import neo4j3d.core.geom.Point;

public class Seeds {

	public static List<Point> apply(List<BBox> volumes, final int kmax) {

		int length = volumes.size();
		int[] seedStraight = new int[2]; // Maximum straight (index1, index2)
		double limitDistance = 0D;
		double distance = 0D;

		for (int i = 0; i < length; i++) {
			BBox vol1 = volumes.get(i);
			for (int j = i + 1; j < length; j++) {
				BBox vol2 = volumes.get(j);
				distance = vol1.distanceOf(vol2);
				if (distance > limitDistance) {
					limitDistance = distance;
					seedStraight[0] = i;
					seedStraight[1] = j;
					i = j;
				}
			}
		}

		LinkedList<Point> deque = new LinkedList<Point>();
		deque.add(volumes.get(seedStraight[0]).getPoint());
		deque.add(volumes.get(seedStraight[1]).getPoint());

		final int kmaxG = kmax - 1; // look at up
		double distances[] = new double[kmaxG];
		distances[0] = limitDistance;

		// calculate straight vector formula
		double straight[] = makeStraightVector(deque.get(0), deque.get(1));

		for (int i = 0; i < length; i++) {
			if (i != seedStraight[0] && i != seedStraight[1]) {
				distance = distance(volumes.get(i).getPoint(), straight);
				l1: for (int j = 1; j < kmaxG; j++) {
					if (distance > distances[j]) {
						for (int k = kmaxG - 1; k > j; k--)
							distances[k] = distances[k - 1];

						distances[j] = distance;
						deque.add(j, volumes.get(i).getPoint());
						if (deque.size() > kmax)
							deque.removeLast();

						break l1;
					}
				}
			}
		}

		return deque;
	}

	/*
	 * Geodesic distance
	 */
	private static double distance(Point point, double[] straight) {

		double x = point.x - straight[0];
		double y = point.y - straight[1];
		double z = point.z - straight[2];

		if (x < 0)
			x = 0 - x;
		if (y < 0)
			y = 0 - y;
		if (z < 0)
			z = 0 - z;

		return x + y + z;
	}

	private static double[] makeStraightVector(Point q, Point t) {
		return new double[] { q.x, q.y, q.z, t.x - q.x, t.y - q.y, t.z - q.z };
	}
}
