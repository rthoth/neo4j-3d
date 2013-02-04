package org.neo4j.gis3d.api;

import org.neo4j.graphdb.Node;

import com.vividsolutions.jts.geom.Geometry;

public interface Layer {

	public Node add(Geometry geom);
}
