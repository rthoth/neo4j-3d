package neo4j3D.api;

<<<<<<< HEAD:src/main/java/neo4j3D/api/Layer.java
import neo4j3D.core.Geometry;

import org.neo4j.graphdb.Node;

=======
import org.neo4j.gis3d.core.Geometry;
import org.neo4j.graphdb.Node;

>>>>>>> f9dcce5e6cef47339a9d2253ecac298908b94baa:src/main/java/org/neo4j/gis3d/api/Layer.java
public interface Layer {

	public Node add(Geometry geom);
}
