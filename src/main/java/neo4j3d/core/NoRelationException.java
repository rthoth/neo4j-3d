package neo4j3d.core;

import org.neo4j.graphdb.RelationshipType;

public class NoRelationException extends RuntimeException {

	public NoRelationException(RelationshipType relationShipType) {
		super(relationShipType.name());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
