package neo4j3d.core;

import java.util.Map;

import neo4j3d.api.Index;
import neo4j3d.api.Layer;
import neo4j3d.api.LoadIndexException;
import neo4j3d.api.SpatialNode;
import neo4j3d.geom.Geometry;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class LayerImpl implements Layer {

	private Node node;
	private Index index;
	private GraphDatabaseService gds;
	private Map<String, Object> properties;

	public LayerImpl(Node node, GraphDatabaseService gds) {
		this(node, gds, null);
	}

	public LayerImpl(Node node, GraphDatabaseService gds,
			Map<String, Object> properties) {
		this.node = node;
		this.gds = gds;
		this.properties = properties;
	}

	@Override
	public Layer add(Node node, Geometry geometry) {
		getIndex().add(SpatialNode.from(node, geometry));
		return this;
	}

	private Index getIndex() {
		if (index == null) {
			Relationship relation = node.getSingleRelationship(
					SpatialRelationShipType.LAYER_INDEX, Direction.OUTGOING);

			if (relation == null)
				throw new NoRelationException(SpatialRelationShipType.LAYER_INDEX);
			try {
				index = Neo4j3D.loadIndex(relation.getEndNode(), gds, properties);
			} catch (Exception e) {
				throw new LoadIndexException(e);
			}
		}
		return index;
	}

	@Override
	public Node getRoot() {
		return node;
	}

}
