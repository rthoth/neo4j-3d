package neo4j3d.core.cluster;

import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import java.util.LinkedList;
import java.util.List;

import neo4j3d.core.BBox;
import neo4j3d.core.Tuple2;
import neo4j3d.core.Tuple3;
import neo4j3d.core.geom.Point;

public class ClusterSeeder {

	public static List<Point> apply(List<BBox> volumes, final int kmax) {

		Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> maxStraight = searchMaxStraight(volumes);

		Point startPoint = maxStraight._1._2.getCenter();
		Point endPoint = maxStraight._2._2.getCenter();

		int startIndex = maxStraight._1._1;
		int endIndex = maxStraight._2._1;

		double[] straight = makeStraightVector(startPoint, endPoint);

		int kmaxDyn = kmax - 1;
		double[] distances = new double[kmaxDyn];

		LinkedList<Point> deque = new LinkedList<Point>();

		for (int i = 0; i < volumes.size(); i++) {
			if (i != startIndex && i != endIndex) {
				final Point point = volumes.get(i).getCenter();
				double distance = distance(point, straight);
				for (int d = 0; d < distances.length; d++) {
					if (distance > distances[d]) {
						for (int k = distances.length - 1; k > d; k--)
							distances[k] = distances[k - 1];
						distances[d] = distance;
						deque.add(min(d, deque.size()), point);

						if (deque.size() == kmaxDyn)
							deque.removeLast();
						break;
					}
				}
			}
		}

		deque.push(endPoint);
		deque.push(startPoint);

		return deque;
	}

	public static Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> searchMaxStraight(
			List<BBox> volumes) {

		Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> endTuple = searchFartherstOf(
				0, volumes);

		Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> startTuple = searchFartherstOf(
				endTuple._2._1, volumes);

		return Tuple3.from(startTuple._2, endTuple._2, startTuple._3);
	}

	private static Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> searchFartherstOf(
			int startIndex, List<BBox> volumes) {
		final BBox startVol = volumes.get(startIndex);
		double length = 0D, maxLength = 0D;
		int endIndex = startIndex;
		BBox vol = null;
		for (int i = 0; i < volumes.size(); i++) {
			if (i != startIndex) {
				final BBox endVol = volumes.get(i);
				length = startVol.distanceOf(endVol);
				if (length > maxLength) {
					vol = endVol;
					endIndex = i;
					maxLength = length;
				}
			}
		}

		return Tuple3.from(Tuple2.from(startIndex, startVol),
				Tuple2.from(endIndex, vol), maxLength);
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
