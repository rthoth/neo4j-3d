package neo4j3D.core;

import org.neo4j.graphdb.GraphDatabaseService;

public interface TXE<R> {

	R apply(GraphDatabaseService gds);

}
