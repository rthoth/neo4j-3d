package neo4j3d.core.cluster;

import java.util.LinkedList;
import java.util.List;

import neo4j3d.core.BBox;
import neo4j3d.core.Tuple2;
import neo4j3d.core.geom.Point;

public class ClusterSeeder {

	public static List<Point> apply(List<BBox> volumes, final int kmax) {

		int length = volumes.size();
		double limitDistance = 0D;
		double distance = 0D;

		Tuple2<Integer, Integer> maxStraight = searchMaxStraight(volumes);

		int maxStraightStart = maxStraight._1, maxStraightEnd = maxStraight._2;

		LinkedList<Point> deque = new LinkedList<Point>();
		deque.add(volumes.get(maxStraightStart).getCenter());
		deque.add(volumes.get(maxStraightEnd).getCenter());

		final int kmaxG = kmax - 1; // look at up
		double distances[] = new double[kmaxG];
		distances[0] = limitDistance;

		// calculate straight vector formula
		double straight[] = makeStraightVector(deque.get(0), deque.get(1));

		for (int i = 0; i < length; i++) {
			if (i != maxStraightStart && i != maxStraightEnd) {
				distance = distance(volumes.get(i).getCenter(), straight);
				l1: for (int j = 1; j < kmaxG; j++) {
					if (distance > distances[j]) {
						for (int k = kmaxG - 1; k > j; k--)
							distances[k] = distances[k - 1];

						distances[j] = distance;
						deque.add(j, volumes.get(i).getCenter());
						if (deque.size() > kmax)
							deque.removeLast();

						break l1;
					}
				}
			}
		}

		return deque;
	}

	public static Tuple2<Integer, Integer> searchMaxStraight(List<BBox> volumes) {
		final int length = volumes.size();

		double distance = 0D, limitDistance = 0D;

		int start = 0, end = 0;

		for (int i = 0; i < length; i++) {
			BBox vol1 = volumes.get(i);
			for (int j = i + 1; j < length; j++) {
				BBox vol2 = volumes.get(j);
				distance = vol1.distanceOf(vol2);
				if (distance > limitDistance) {
					limitDistance = distance;
					start = i;
					end = j;
					i = j;
				}
			}
		}

		return new Tuple2<Integer, Integer>(start, end);
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
