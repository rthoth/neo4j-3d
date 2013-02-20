package neo4j3d.core.index.osri;

import neo4j3d.api.SpatialNode;
import neo4j3d.api.SpatialProperties;
import neo4j3d.core.SpatialRelationShipType;
import neo4j3d.core.index.InvalidExtentException;
import neo4j3d.core.index.InvalidLimitException;
import neo4j3d.geom.BBOX;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class OcTree {

	@SuppressWarnings("unused")
	private Node node;
	private Iterable<Relationship> iterator;
	private int limit;
	private BBOX bbox;

	public OcTree(Node node) {
		this.node = node;
		iterator = node.getRelationships(SpatialRelationShipType.INDEX_LINK,
				Direction.OUTGOING);

	}

	/**
	 * @param spatial
	 * @return
	 */
	public Node add(SpatialNode spatial) {

		return null;
	}

	public boolean hasUniverse() {
		if (node.hasProperty(SpatialProperties.EXTENT)) {

			double[] extent = null;
			try {
				extent = (double[]) node.getProperty(SpatialProperties.EXTENT);
			} catch (Exception e) {
				throw new InvalidExtentException(e);
			}

			if (extent.length == 6) {

				if (node.hasProperty(SpatialProperties.LIMIT)) {
					try {
						this.limit = (Integer) node.getProperty(SpatialProperties.LIMIT);
					} catch (Exception e) {
						throw new InvalidLimitException(e);
					}
				}

				this.bbox = BBOX.from(extent);

			} else
				throw new InvalidExtentException("Invalid array length "
						+ extent.length + ", should be 6");
		}

		return false;
	}

	public void universe(BBOX universe, int limit) {
		node.setProperty(SpatialProperties.EXTENT, universe.toArray());
		node.setProperty(SpatialProperties.LIMIT, limit);

		this.limit = limit;
		this.bbox = universe;
	}

}
