package neo4j3D.server;

import neo4j3D.api.Layer;
import neo4j3D.core.DefaultSpatialGraphDatabaseService;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.server.plugins.Parameter;
import org.neo4j.server.plugins.ServerPlugin;
import org.neo4j.server.plugins.Source;

public class Plugin extends ServerPlugin {

	public Layer createLayer(@Source final GraphDatabaseService gds,
			@Parameter(name = "name") final String name) {

		return new DefaultSpatialGraphDatabaseService(gds).getOrCreateLayer(name);
	}
}
