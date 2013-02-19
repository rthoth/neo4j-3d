package neo4j3d.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import neo4j3d.api.Index;
import neo4j3d.api.SpatialProperties;
import neo4j3d.core.index.osri.OSRI;

import org.neo4j.graphdb.Node;

public class Neo4j3D {

	@SuppressWarnings("unchecked")
	public static Index loadIndex(Node node) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (node != null) {
			String clazzName = (String) node
					.getProperty(SpatialProperties.INDEX_CLASS);
			Class<Index> clazz = (Class<Index>) Thread.currentThread()
					.getContextClassLoader().loadClass(clazzName);
			Constructor<Index> constructor = clazz.getConstructor(Node.class);

			return constructor.newInstance(node);
		} else
			throw new NullNodeException();
	}

	public static String defaultIndexClass() {
		return OSRI.class.getName();
	}

}
