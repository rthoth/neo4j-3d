package neo4j3d.api;

import java.util.Map;

import neo4j3d.core.LayerImpl;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;

public abstract class SpatialGraphDatabaseService {

	private static final String LAYERS_INDEX = "layers";

	protected GraphDatabaseService gds;

	public SpatialGraphDatabaseService(GraphDatabaseService gds) {
		this.gds = gds;
	}

	protected abstract Layer createLayer(String name, Index<Node> index,
			Map<String, Object> properties);

	public Layer getLayer(String name) {
		Index<Node> index = getNodeIndex(LAYERS_INDEX);
		Node node = index.get(SpatialProperties.LAYER_NAME, name).getSingle();

		if (node == null)
			throw new NoResultException("Layer " + name);

		return new LayerImpl(node, gds);
	}

	protected Index<Node> getNodeIndex(String name) {
		return gds.index().forNodes(name);
	}

	public Layer getOrCreateLayer(String name, Map<String, Object> properties) {
		try {
			return getLayer(name);
		} catch (NoResultException e) {
			return createLayer(name, getNodeIndex(LAYERS_INDEX), properties);
		}
	}

}
