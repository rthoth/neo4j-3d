package neo4j3d.core;

import neo4j3d.api.Index;
import neo4j3d.api.SpatialProperties;
import neo4j3d.core.index.osri.OSRI;

import org.neo4j.graphdb.Node;

public class Neo4j3D {

	public static Index loadIndex(Node node) throws Exception {

		if (node != null) {
			Class<? extends Index> clazz = null;
			if (node.hasProperty(SpatialProperties.INDEX_CLASS))
				clazz = loadClass((String) node
						.getProperty(SpatialProperties.INDEX_CLASS));
			else
				clazz = OSRI.class;

			return clazz.newInstance();
		} else
			throw new NullNodeException();
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> loadClass(String className)
			throws ClassNotFoundException {
		return (Class<T>) Thread.currentThread().getContextClassLoader()
				.loadClass(className);
	}

	public static String defaultIndexClass() {
		return OSRI.class.getName();
	}

}
