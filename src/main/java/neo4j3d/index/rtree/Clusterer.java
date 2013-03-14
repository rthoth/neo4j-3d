package neo4j3d.index.rtree;

import java.util.List;

import neo4j3d.geom.BBox;
import neo4j3d.index.Cluster;

import com.google.common.collect.ImmutableList;

public class Clusterer {

	public final int maxSize;

	public Clusterer(int maxSize) {
		this.maxSize = maxSize;
	}

	public ImmutableList<Cluster> apply(ImmutableList<BBox> objects) {

		Cluster cluster = new ClusterImpl(objects.get(0));

		List<Cluster> seeds = Seeder.apply(cluster,
				objects.subList(1, objects.size()));

		System.out.println(seeds);

		return ImmutableList.copyOf(seeds);
	}

}
