package org.neo4j.gis3d.api;

import org.neo4j.gis3d.core.Geometry;
import org.neo4j.graphdb.Node;

public interface LayerIndex {

	public Node add(Geometry geom);

}
