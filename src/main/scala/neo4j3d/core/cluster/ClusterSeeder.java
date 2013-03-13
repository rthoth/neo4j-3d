package neo4j3d.core.cluster;

import static neo4j3d.core.Math.distance;
import static neo4j3d.core.Math.straight;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import neo4j3d.core.BBox;
import neo4j3d.core.Math.Straight;
import neo4j3d.core.Tuple2;
import neo4j3d.core.Tuple3;
import neo4j3d.core.geom.Point;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class ClusterSeeder {

	public static List<Point> apply(List<BBox> volumes, int kmax) {

		Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> currentStraight = searchMaxStraight(volumes);

		Point startPoint = currentStraight._1._2.getCenter();
		Point endPoint = currentStraight._2._2.getCenter();

		int startIndex = currentStraight._1._1;
		int endIndex = currentStraight._2._1;

		Straight straight = straight(startPoint, endPoint);
		SortedSet<Integer> indexes = Sets.newTreeSet();
		indexes.add(startIndex);
		indexes.add(endIndex);

		List<Point> seeds = Lists.newArrayListWithCapacity(kmax);
		seeds.add(startPoint);
		seeds.add(endPoint);

		kmax -= 2;
		for (int k = 0; k < kmax; k++) {
			Tuple2<Integer, Point> tuple = fartherstOf(volumes, straight, indexes);
			indexes.add(tuple._1);
			seeds.add(tuple._2);
			straight = straight(tuple._2, straight.center());
		}

		return seeds;
	}

	/*
	 * 
	 */

	private static Tuple2<Integer, Point> fartherstOf(List<BBox> volumes,
			Straight straight, Collection<Integer> exclude) {
		double distance, maxDistance = 0D;
		int index = 0;
		for (int i = 0; i < volumes.size(); i++) {
			if (!exclude.contains(i)) {
				distance = distance(volumes.get(i).getCenter(), straight);
				if (distance > maxDistance) {
					maxDistance = distance;
					index = i;
				}
			}
		}

		return Tuple2.from(index, volumes.get(index).getCenter());
	}

	private static Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> fartherstOf(
			int startIndex, List<BBox> volumes) {

		final BBox startVol = volumes.get(startIndex);

		double length = 0D, maxLength = 0D;
		int endIndex = startIndex;
		for (int i = 0; i < volumes.size(); i++) {
			if (i != startIndex) {
				final BBox endVol = volumes.get(i);
				length = startVol.distanceOf(endVol);
				if (length > maxLength) {
					endIndex = i;
					maxLength = length;
				}
			}
		}

		return Tuple3.from(Tuple2.from(startIndex, startVol),
				Tuple2.from(endIndex, volumes.get(endIndex)), maxLength);
	}

	public static Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> searchMaxStraight(
			List<BBox> volumes) {

		int endIndex = fartherstOf(0, volumes)._2._1;

		Tuple3<Tuple2<Integer, BBox>, Tuple2<Integer, BBox>, Double> maxStraight = fartherstOf(
				endIndex, volumes);

		return Tuple3.from(maxStraight._2, maxStraight._1, maxStraight._3);
	}
}
