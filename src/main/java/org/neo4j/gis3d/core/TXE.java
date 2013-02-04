package org.neo4j.gis3d.core;

import org.neo4j.graphdb.GraphDatabaseService;

public interface TXE<R> {

	R apply(GraphDatabaseService gds);

}
