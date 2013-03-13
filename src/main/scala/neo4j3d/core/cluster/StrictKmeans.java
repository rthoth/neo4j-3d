package neo4j3d.core.cluster;

import java.util.ArrayList;
import java.util.List;

import neo4j3d.core.BBox;
import neo4j3d.core.Tuple2;
import neo4j3d.core.geom.Point;

public class StrictKmeans {

	private static final int LIMIT = 10;

	public static Tuple2<Double, List<Cluster>> apply(final List<Point> centers,
			final List<BBox> volumes) {

		List<Cluster> clusters = clustersFrom(centers);

		group(clusters, volumes);

		return Tuple2.from(calculateW(clusters), clusters);
	}

	private static List<Cluster> clustersFrom(List<Point> centers) {

		List<Cluster> clusters = new ArrayList<Cluster>(centers.size());
		for (Point center : centers) {
			clusters.add(new ClusterImpl(center));
		}
		return clusters;
	}

	private static Cluster findNearest(BBox volume, List<Cluster> clusters) {
		double minDistance = Double.POSITIVE_INFINITY, distance;

		Cluster cluster = null;
		for (int i = 0; i < clusters.size(); i++) {
			distance = volume.distanceOf(clusters.get(i));
			if (distance < minDistance) {
				cluster = clusters.get(i);
				minDistance = distance;
			}
		}

		return cluster;
	}

	private static void group(List<Cluster> clusters, List<BBox> volumes) {
		boolean changed = true;
		int i = 0;
		do {
			changed = false;
			for (BBox volume : volumes) {
				Cluster nearest = findNearest(volume, clusters);
				if (nearest != null) {
					if (nearest.add(volume))
						changed = true;
				} else
					throw new KmeansException("No nearest cluster from " + volume);
			}
		} while (changed && (++i) < LIMIT);
	}

	private static double calculateW(List<Cluster> clusters) {

		double coverage = 0D, shape = 0D, overlap = 0D;

		for (int i = 0; i < clusters.size(); i++) {
			Cluster cluster = clusters.get(i);

			coverage += cluster.coverage();
			shape += cluster.shape();

			for (int j = i + 1; j < clusters.size(); j++) {
				overlap += cluster.overlap(clusters.get(j));
			}
		}

		return coverage + shape + overlap;
	}

}
