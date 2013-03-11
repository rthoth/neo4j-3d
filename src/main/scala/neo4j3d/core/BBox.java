package neo4j3d.core;

import neo4j3d.core.cluster.Cluster;
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

	Point getCenter();

	Tuple3<Double, Double, Double> getCenterCoordinates();

	Tuple3<Double, Double, Double> maximum();

	Tuple3<Double, Double, Double> minimum();

	void setCluster(Cluster cluster);

}