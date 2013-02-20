package neo4j3d.api;

import neo4j3d.geom.BBOX;
import neo4j3d.geom.Geometry;

import org.neo4j.graphdb.Node;

public class SpatialNode {

	@SuppressWarnings("unused")
	private BBOX bbox;
	@SuppressWarnings("unused")
	private Node node;

	public SpatialNode(Node node, BBOX bbox) {
		this.node = node;
		this.bbox = bbox;
	}

	public static SpatialNode from(Node node, Geometry geometry) {
		return new SpatialNode(node, geometry.getBBOX());
	}

}
