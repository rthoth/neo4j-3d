package neo4j3d.core.index.osri;

import neo4j3d.api.Index;
import neo4j3d.geom.Geometry;

import org.neo4j.graphdb.Node;

public class OSRI implements Index {

	@SuppressWarnings("unused")
	private Node node;

	public OSRI(Node node) {
		this.node = node;
	}

	@Override
	public Index add(Geometry geometry) {
		// TODO Auto-generated method stub
		return null;
	}

}
