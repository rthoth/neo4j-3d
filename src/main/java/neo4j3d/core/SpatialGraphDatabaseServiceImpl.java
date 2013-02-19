package neo4j3d.core;

import neo4j3d.api.Layer;
import neo4j3d.api.SpatialGraphDatabaseService;
import neo4j3d.api.SpatialProperties;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;

public class SpatialGraphDatabaseServiceImpl extends
		SpatialGraphDatabaseService {

	public SpatialGraphDatabaseServiceImpl(GraphDatabaseService gds) {
		super(gds);
	}

	@Override
	protected Layer createLayer(final String name, final Index<Node> index) {
		return TX.tx(gds, new TXE<Layer>() {
			@Override
			public Layer apply(GraphDatabaseService gds) {
				Node layerNode = gds.createNode();
				layerNode.setProperty(SpatialProperties.LAYER_NAME, name);
				index.add(layerNode, SpatialProperties.LAYER_NAME, name);

				Node indexNode = gds.createNode();
				indexNode.setProperty(SpatialProperties.INDEX_CLASS,
						Neo4j3D.defaultIndexClass());

				layerNode.createRelationshipTo(indexNode,
						SpatialRelationShipType.LAYER_INDEX);

				return new LayerImpl(layerNode);
			}
		});
	}
}
