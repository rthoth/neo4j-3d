package org.neo4j.gis3d.core.index;

import org.neo4j.gis3d.api.LayerIndex;
import org.neo4j.gis3d.core.Boundary;
import org.neo4j.gis3d.core.Geometry;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.kernel.Traversal;

/**
 * Hybrid 3D Spatial index.
 * 
 * Based on
 * 
 * "Research on a Hybrid Spatial Index Structure" by "Weijie GU", "Jishui WANG",
 * "Hao SHI", "Yongshan LIU"
 * 
 * @author rthoth
 */
public class OSRItree implements LayerIndex {

	private Node node;
	private GraphDatabaseService gds;

	public OSRItree(Node node, GraphDatabaseService gds) {
		this.node = node;
		this.gds = gds;
	}

	@Override
	public Node add(Geometry geometry) {
		Boundary boundary = geometry.getBoundary();
		Traversal.description().breadthFirst()
				.evaluator(new BoundaryEvaluator(boundary));

		return null;
	}

}
