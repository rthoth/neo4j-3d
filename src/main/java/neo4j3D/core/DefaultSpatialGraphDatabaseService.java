package neo4j3D.core;

import org.neo4j.graphdb.GraphDatabaseService;

import neo4j3D.api.SpatialGraphDatabaseService;

public class DefaultSpatialGraphDatabaseService extends
		SpatialGraphDatabaseService {

	public DefaultSpatialGraphDatabaseService(GraphDatabaseService gds) {
		super(gds);
	}

}
