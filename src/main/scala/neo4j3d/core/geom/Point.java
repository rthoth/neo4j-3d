package neo4j3d.core.geom;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import neo4j3d.core.BBox;
import neo4j3d.core.Tuple3;
import neo4j3d.core.cluster.Cluster;

public class Point implements BBox {

	@SuppressWarnings("unused")
	private Cluster cluster;
	private Tuple3<Double, Double, Double> coordinates = null;
	public final double x;
	public final double y;
	public final double z;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public double distanceOf(BBox other) {
		Tuple3<Double, Double, Double> center = other.getCenterCoordinates();
		/*
		 * return sqrt(pow(center[0] - x, 2) + pow(center[1] - y, 2) + pow(center[2]
		 * - z, 2));
		 */
		return sqrt(pow(center._1 - x, 2) + pow(center._2 - y, 2)
				+ pow(center._3 - z, 2));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point)
			return equalsPoint((Point) obj);

		return false;

	}

	private boolean equalsPoint(Point obj) {
		return obj.x == x && obj.y == y && obj.z == z;
	}

	@Override
	public Point getCenter() {
		return this;
	}

	@Override
	public Tuple3<Double, Double, Double> getCenterCoordinates() {
		if (coordinates != null)
			return coordinates;
		else
			return coordinates = new Tuple3<Double, Double, Double>(x, y, z);
	}

	@Override
	public Tuple3<Double, Double, Double> maximum() {
		return getCenterCoordinates();
	}

	@Override
	public Tuple3<Double, Double, Double> minimum() {
		return getCenterCoordinates();
	}

	@Override
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}