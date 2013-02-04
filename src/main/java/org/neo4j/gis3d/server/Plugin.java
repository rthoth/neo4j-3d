package org.neo4j.gis3d.server;

import org.neo4j.gis3d.api.Gis3DDatabaseService;
import org.neo4j.gis3d.api.Layer;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.server.plugins.Parameter;
import org.neo4j.server.plugins.ServerPlugin;
import org.neo4j.server.plugins.Source;

public class Plugin extends ServerPlugin {

	public Layer createLayer(@Source final GraphDatabaseService gds,
			@Parameter(name = "name") final String name) {

		return new Gis3DDatabaseService(gds).getOrCreateLayer(name);
	}
}
