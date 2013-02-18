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
<<<<<<< HEAD:src/main/java/neo4j3D/api/SpatialGraphDatabaseService.java
			if (layerName.equals(layer.getProperty(Properties.LAYER_NAME))) {
=======
			if (layerName.equals(layer.getProperty(Gis3DProperties.LAYER_NAME))) {
>>>>>>> f9dcce5e6cef47339a9d2253ecac298908b94baa:src/main/java/org/neo4j/gis3d/api/Gis3DDatabaseService.java
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
<<<<<<< HEAD:src/main/java/neo4j3D/api/SpatialGraphDatabaseService.java
						RelationshipTypes.LAYER);
=======
						Gis3DRelationshipTypes.LAYER);
>>>>>>> f9dcce5e6cef47339a9d2253ecac298908b94baa:src/main/java/org/neo4j/gis3d/api/Gis3DDatabaseService.java
				return new DefaultLayer(node, gds);
			}
		});
	}
}
