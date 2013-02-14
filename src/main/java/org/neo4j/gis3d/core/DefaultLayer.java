package org.neo4j.gis3d.core;

import org.neo4j.gis3d.Gis3DRelationshipTypes;
import org.neo4j.gis3d.api.Layer;
import org.neo4j.gis3d.api.LayerIndex;
import org.neo4j.gis3d.core.index.OSRItree;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class DefaultLayer implements Layer {

	private Node layerNode;
	private LayerIndex index;
	private GraphDatabaseService gds;

	public DefaultLayer(Node node, GraphDatabaseService gds) {
		this.layerNode = node;
		this.gds = gds;
	}

	@Override
	public Node add(Geometry geom) {
		return this.getIndex().add(geom);
	}

	private LayerIndex getIndex() {
		if (index == null) {
			Relationship relation = this.layerNode.getSingleRelationship(
					Gis3DRelationshipTypes.LAYER_INDEX, Direction.OUTGOING);

			Node indexNode = null;
			if (relation == null) {
				indexNode = TX.tx(gds, new TXE<Node>() {
					@Override
					public Node apply(GraphDatabaseService gds) {
						Node indexNode = gds.createNode();
						layerNode.createRelationshipTo(indexNode,
								Gis3DRelationshipTypes.LAYER_INDEX);
						return indexNode;
					}
				});
			} else
				indexNode = relation.getEndNode();

			index = new OSRItree(indexNode, gds);
		}
		return index;
	}
}
