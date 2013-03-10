package neo4j3d.core.cluster;

import neo4j3d.core.BBox;

public interface Cluster extends BBox {

	boolean add(BBox volume);

	double coverage();

	double overlap(Cluster other);

	double shape();

}
