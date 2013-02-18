package neo4j3D.core.index;

import neo4j3D.Properties;
import neo4j3D.RelationshipTypes;
import neo4j3D.core.Boundary;

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
				.getProperty(Properties.BOUNDARY));
		boolean hasSub = false;

		if (within)
			hasSub = current.hasRelationship(RelationshipTypes.INDEX,
					Direction.OUTGOING);

		return Evaluation.of(!hasSub && within, !hasSub);
	}
}
