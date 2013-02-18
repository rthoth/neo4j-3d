package neo4j3D.api;

import neo4j3D.core.Geometry;

import org.neo4j.graphdb.Node;

public interface Layer {

	public Node add(Geometry geom);
}
