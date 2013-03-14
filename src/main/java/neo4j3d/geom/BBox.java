package neo4j3d.geom;

import neo4j3d.Tuple3;

public interface BBox {

	Point getCenter();

	Tuple3<Double, Double, Double> getMaximum();

	Tuple3<Double, Double, Double> getMinimum();

}