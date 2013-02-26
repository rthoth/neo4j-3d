package neo4j3d.api;

import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public interface Index {

	Index add(final SpatialNode spatial);

	Index start(Node node, GraphDatabaseService gds,
			Map<String, Object> properties);

}
