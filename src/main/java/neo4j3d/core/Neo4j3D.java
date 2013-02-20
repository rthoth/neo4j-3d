package neo4j3d.core;

import java.lang.reflect.Constructor;
import java.util.Map;

import neo4j3d.api.Index;
import neo4j3d.api.SpatialProperties;
import neo4j3d.core.index.osri.OSRI;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public class Neo4j3D {

	@SuppressWarnings("unchecked")
	public static Index loadIndex(Node node, GraphDatabaseService gds,
			Map<String, Object> properties) throws Exception {

		if (node != null) {
			String clazzName = (String) node
					.getProperty(SpatialProperties.INDEX_CLASS);
			Class<Index> clazz = (Class<Index>) Thread.currentThread()
					.getContextClassLoader().loadClass(clazzName);
			Constructor<Index> constructor = clazz.getConstructor(Node.class,
					GraphDatabaseService.class, Map.class);

			return constructor.newInstance(node, gds, properties);
		} else
			throw new NullNodeException();
	}

	public static String defaultIndexClass() {
		return OSRI.class.getName();
	}

}
