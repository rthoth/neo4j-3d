package neo4j3d.core.index.osri;

import java.util.Map;

import neo4j3d.api.Index;
import neo4j3d.api.SpatialNode;
import neo4j3d.core.index.InvalidPropertiesException;
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

	private OcTree octree;

	private BBOX universe;

	private int limit;

	public OSRI(Node node, GraphDatabaseService gds,
			Map<String, Object> properties) {
		this.gds = gds;
		octree = new OcTree(node);
		if (!octree.hasUniverse()) {
			if (properties != null) {
				double[] bbox = (double[]) properties.get("extent");
				int limit = (Integer) properties.get("limit");

				octree.universe(BBOX.from(bbox), limit);
			} else
				throw new InvalidPropertiesException("Properties are null");

		}
	}

	@Override
	public Index add(final SpatialNode spatial) {
		Node rTreeRoot = octree.add(spatial);
		return this;
	}

}
