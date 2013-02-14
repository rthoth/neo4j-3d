package org.neo4j.gis3d.api;

import org.neo4j.gis3d.Gis3DProperties;
import org.neo4j.gis3d.Gis3DRelationshipTypes;
import org.neo4j.gis3d.core.DefaultLayer;
import org.neo4j.gis3d.core.TX;
import org.neo4j.gis3d.core.TXE;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class Gis3DDatabaseService {

	private GraphDatabaseService gds;

	public Gis3DDatabaseService(GraphDatabaseService gds) {
		this.gds = gds;
	}

	public Layer getOrCreateLayer(String layerName) {

		for (Relationship relation : gds.getReferenceNode().getRelationships(
				Gis3DRelationshipTypes.LAYER, Direction.OUTGOING)) {
			final Node layer = relation.getEndNode();
			if (layerName.equals(layer.getProperty(Gis3DProperties.LAYER_NAME))) {
				return new DefaultLayer(layer, gds);
			}
		}

		return createLayer(layerName);
	}

	private Layer createLayer(final String layerName) {
		return TX.tx(gds, new TXE<Layer>() {

			public Layer apply(GraphDatabaseService gds) {
				Node node = gds.createNode();
				node.setProperty(Gis3DProperties.LAYER_NAME, layerName);
				gds.getReferenceNode().createRelationshipTo(node,
						Gis3DRelationshipTypes.LAYER);
				return new DefaultLayer(node, gds);
			}
		});
	}
}
