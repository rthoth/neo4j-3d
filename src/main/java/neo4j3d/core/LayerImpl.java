package neo4j3d.core;

import neo4j3d.api.Index;
import neo4j3d.api.Layer;
import neo4j3d.api.LoadIndexException;
import neo4j3d.geom.Geometry;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class LayerImpl implements Layer {

	private Node node;
	private Index index;

	public LayerImpl(Node node) {
		this.node = node;
	}

	@Override
	public Layer add(Geometry geometry) {
		getIndex().add(geometry);
		return this;
	}

	private Index getIndex() {
		if (index == null) {
			Relationship relation = node.getSingleRelationship(
					SpatialRelationShipType.LAYER_INDEX, Direction.OUTGOING);

			if (relation == null)
				throw new NoRelationException(SpatialRelationShipType.LAYER_INDEX);

			try {
				index = Neo4j3D.loadIndex(relation.getEndNode());
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
