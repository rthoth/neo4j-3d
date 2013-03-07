package neo4j3d.core.cluster;

import java.util.List;

import neo4j3d.core.BBox;

public interface Clusterer {

	List<Cluster> cluster(List<BBox> volumes);
}
