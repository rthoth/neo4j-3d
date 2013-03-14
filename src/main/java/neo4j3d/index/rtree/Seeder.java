package neo4j3d.index.rtree;

import java.util.List;

import neo4j3d.geom.BBox;
import neo4j3d.geom.Point;
import neo4j3d.index.Cluster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Seeder {

	public static List<Cluster> apply(Cluster cluster, ImmutableList<BBox> objects) {

		Cluster[] children = new Cluster[9];
		children[0] = cluster;

		final Point center = cluster.getCenter();

		for (BBox object : objects) {
			final Point otherCenter = object.getCenter();
			final int cIndex = childrenIndex(center, otherCenter);
			if (children[cIndex] != null)
				children[cIndex].add(object);
			else
				children[cIndex] = new ClusterImpl(object);
		}

		List<Cluster> result = Lists.newLinkedList();
		for (Cluster candidate : children) {
			if (candidate != null)
				result.add(candidate);
		}

		return result;

	}

	private static int childrenIndex(Point center, Point other) {

		double left = other.x - center.x;
		double front = other.y - center.y;
		double top = other.z - center.z;
		int index = 0;

		if (left > 0) {
			if (front > 0)
				index = 1;
			else if (front < 0)
				index = 4;
		} else if (left < 0) {
			if (front > 0)
				index = 2;
			else if (front < 0)
				index = 3;
		}

		if (index != 0 && top > 0)
			index += 4;

		return index;
	}
}
