package neo4j3d.api;

import neo4j3d.geom.Geometry;

import org.neo4j.graphdb.Node;

public interface Layer {

	public Node getRoot();

	public Layer add(Geometry geometry);

}
