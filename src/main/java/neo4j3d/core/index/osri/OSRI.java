package neo4j3d.core.index.osri;

import java.util.Map;

import neo4j3d.api.Index;
import neo4j3d.api.SpatialNode;
import neo4j3d.api.SpatialProperties;
import neo4j3d.geom.BBOX;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

/**
 * 
 * 
 * Based on "Research on a Hybrid Spatial Index Structure" by
 * "Weijie GU, Jishui WANG, Hao SHI, Yongshan LIU"
 * 
 * @author rthoth
 */
public class OSRI implements Index {

	@SuppressWarnings("unused")
	private GraphDatabaseService gds;

	@SuppressWarnings("unused")
	private BBOX universe;

	@SuppressWarnings("unused")
	private int limit;

	private Node root;

	@Override
	public Index add(final SpatialNode spatial) {
		return this;
	}

	@Override
	public Index start(Node node, GraphDatabaseService gds,
			Map<String, Object> properties) {
		root = node;

		if (root.hasProperty(SpatialProperties.EXTENT)) {
			double[] extent = (double[]) root.getProperty(SpatialProperties.EXTENT);
		}

		return this;
	}

}
