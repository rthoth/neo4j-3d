package neo4j3d.index;

import neo4j3d.geom.BBox;

public interface Cluster extends BBox {

	boolean add(BBox object);

}