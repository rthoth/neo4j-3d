package neo4j3D.api;

import neo4j3D.Properties;
import neo4j3D.RelationshipTypes;
import neo4j3D.core.DefaultLayer;
import neo4j3D.core.TX;
import neo4j3D.core.TXE;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public abstract class SpatialGraphDatabaseService {

	private GraphDatabaseService gds;

	public SpatialGraphDatabaseService(GraphDatabaseService gds) {
		this.gds = gds;
	}

	public Layer getOrCreateLayer(String layerName) {

		for (Relationship relation : gds.getReferenceNode().getRelationships(
				RelationshipTypes.LAYER, Direction.OUTGOING)) {
			final Node layer = relation.getEndNode();
			if (layerName.equals(layer.getProperty(Properties.LAYER_NAME))) {
				return new DefaultLayer(layer, gds);
			}
		}

		return createLayer(layerName);
	}

	public Layer getLayer(String name) {
		return null;
	}

	private Layer createLayer(final String layerName) {
		return TX.tx(gds, new TXE<Layer>() {

			public Layer apply(GraphDatabaseService gds) {
				Node node = gds.createNode();
				node.setProperty(Properties.LAYER_NAME, layerName);
				gds.getReferenceNode().createRelationshipTo(node,
						RelationshipTypes.LAYER);
				return new DefaultLayer(node, gds);
			}
		});
	}
}
