package neo4j3d.core.cluster;

import java.util.List;

import neo4j3d.core.BBox;
import neo4j3d.core.Tuple2;
import neo4j3d.core.geom.Point;

/**
 * Based on
 * "An efficient 3D R-tree spatial index method for virtual geographic environments"
 * 
 * by "Qing Zhu, Jun Gong, Yeting Zhang"
 * 
 * @author rthoth
 * 
 */
public class Kmeans implements Clusterer {

	private int minSize;

	public Kmeans(int minSize) {
		this.minSize = minSize;
	}

	@Override
	public List<Cluster> cluster(List<BBox> volumes) {
		final int kmax = volumes.size() / minSize;

		List<Point> seeds = Seeds.apply(volumes, kmax);

		double minW = Double.POSITIVE_INFINITY;
		Tuple2<Double, List<Cluster>> possibleSolution = null;

		for (int k = 2; k <= kmax; k++) {
			Tuple2<Double, List<Cluster>> solution = StrictKmeans.apply(
					seeds.subList(0, k), volumes);

			if (solution._1 < minW) {
				possibleSolution = solution;
				minW = solution._1;
			}
		}

		if (possibleSolution != null)
			return possibleSolution._2;

		return null;
	}

}
