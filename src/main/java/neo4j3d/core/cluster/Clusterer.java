package neo4j3d.core.cluster;

import java.util.List;

import neo4j3d.geom.BBOX;

public interface Clusterer {

	public List<Cluster> apply(List<BBOX> geometries);
}
