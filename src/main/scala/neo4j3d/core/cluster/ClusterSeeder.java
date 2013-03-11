package neo4j3d.core.cluster;

import static java.lang.Math.sqrt;

import java.util.LinkedList;
import java.util.List;

import neo4j3d.core.BBox;
import neo4j3d.core.Tuple3;
import neo4j3d.core.geom.Point;

public class ClusterSeeder {

	public static List<Point> apply(List<BBox> volumes, final int kmax) {

		int length = volumes.size();
		double distance = 0D;

		Tuple3<Integer, Integer, Double> maxStraight = searchMaxStraight(volumes);

		int maxStraightStart = maxStraight._1, maxStraightEnd = maxStraight._2;

		LinkedList<Point> deque = new LinkedList<Point>();
		// deque.add(volumes.get(maxStraightStart).getCenter());
		// deque.add(volumes.get(maxStraightEnd).getCenter());

		final int kmaxG = kmax - 2; // look at up
		double distances[] = new double[kmaxG];
		distances[0] = maxStraight._3;

		// calculate straight vector formula
		double straight[] = makeStraightVector(volumes.get(maxStraightStart)
				.getCenter(), volumes.get(maxStraightEnd).getCenter());

		deque.push(volumes.get(maxStraightStart).getCenter());

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

		deque.add(1, volumes.get(maxStraightEnd).getCenter());

		return deque;
	}

	public static Tuple3<Integer, Integer, Double> searchMaxStraight(
			List<BBox> volumes) {
		final int length = volumes.size();

		double distance = 0D, limitDistance = 0D;

		int start = 0, end = 0;

		for (int iStart = 0; iStart < length; iStart++) {
			BBox vol1 = volumes.get(iStart);
			int currentStart = iStart;

			for (int iEnd = iStart + 1; iEnd < length; iEnd++) {
				BBox vol2 = volumes.get(iEnd);
				distance = vol1.distanceOf(vol2);
				if (distance > limitDistance) {
					limitDistance = distance;
					start = currentStart;
					end = iEnd;
					iStart = iEnd - 1;
				}
			}
		}

		return new Tuple3<Integer, Integer, Double>(start, end, limitDistance);
	}

	/*
	 * 
	 */
	private static double distance(Point point, double[] straight) {

		double x = straight[0] - point.x;
		double y = straight[1] - point.y;
		double z = straight[2] - point.z;

		double vx = straight[3];
		double vy = straight[4];
		double vz = straight[5];

		double i = (y * vz) - (z * vy);
		double j = (z * vx) - (x * vz);
		double k = (x * vy) - (y * vx);

		double den = i * i + j * j + k * k;
		double num = vx * vx + vy * vy + vz * vz;

		return sqrt(den / num);
	}

	private static double[] makeStraightVector(Point q, Point t) {
		return new double[] { q.x, q.y, q.z, t.x - q.x, t.y - q.y, t.z - q.z };
	}
}
