package neo4j3d.core;

import neo4j3d.core.geom.Point;

/**
 * Bounday Volume
 * 
 * 
 * @author rthoth
 * 
 */
public interface BBox {

	double distanceOf(BBox other);

	Tuple3<Double, Double, Double> getCoordinates();

	Point getPoint();

	Tuple3<Double, Double, Double> minimum();

	Tuple3<Double, Double, Double> maximum();

}