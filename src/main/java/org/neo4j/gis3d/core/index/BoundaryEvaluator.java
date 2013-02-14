package org.neo4j.gis3d.core.index;

import org.neo4j.gis3d.Gis3DProperties;
import org.neo4j.gis3d.Gis3DRelationshipTypes;
import org.neo4j.gis3d.core.Boundary;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

public class BoundaryEvaluator implements Evaluator {

	private Boundary boundary;

	public BoundaryEvaluator(Boundary boundary) {
		this.boundary = boundary;
	}

	@Override
	public Evaluation evaluate(Path path) {
		final Node current = path.endNode();

		boolean within = boundary.within((double[]) current
				.getProperty(Gis3DProperties.BOUNDARY));
		boolean hasSub = false;

		if (within)
			hasSub = current.hasRelationship(Gis3DRelationshipTypes.INDEX,
					Direction.OUTGOING);

		return Evaluation.of(!hasSub && within, !hasSub);
	}
}
