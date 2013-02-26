package neo4j3d.core.cluster;

import java.util.LinkedList;
import java.util.List;

import neo4j3d.geom.BBOX;

public class Kmeans implements Clusterer {

	private static class Solution {

	}

	private int min;

	public Kmeans(int min) {
		this.min = min;
	}

	@Override
	public List<Cluster> apply(List<BBOX> bboxes) {
		int kmax = bboxes.size() / min;

		List<BBOX> seeds = seeds(bboxes, kmax);

		Solution solution = null;
		for (int k = 2; k <= kmax; k++) {
			Solution current = solve(bboxes, k);
			if (solution != null) {

			} else
				solution = current;
		}
		return null;
	}

	private Solution solve(List<BBOX> bboxes, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<BBOX> seeds(List<BBOX> bboxes, int kmax) {
		double maxDistance = Double.NEGATIVE_INFINITY;
		BBOX seed = null;
		for (int i = 0; i < bboxes.size(); i++) {
			BBOX b1 = bboxes.get(i);
			b1.disconnect();

			for (int j = i + 1; j < bboxes.size(); j++) {
				BBOX b2 = bboxes.get(j);
				double distance;

				if ((distance = b1.distanceOf(b2)) > maxDistance) {
					maxDistance = distance;
					seed = b1;
				}
				if (distance > b1.getToWeight())
					b1.to(b2, distance);
			}
		}

		List<BBOX> seeds = new LinkedList<BBOX>();
		seeds.add(seed);

		for (int k = 1; k < kmax; k++) {
			BBOX next = seed.to();
		}

		return null;
	}
}
