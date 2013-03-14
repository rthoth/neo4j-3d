package neo4j3d.geom;

import neo4j3d.Tuple3;

public class Point implements BBox {

	public final double x, y, z;
	private Tuple3<Double, Double, Double> coordinates;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static Point from(double x, double y, double z) {
		return new Point(x, y, z);
	}

	@Override
	public Point getCenter() {
		return this;
	}

	@Override
	public Tuple3<Double, Double, Double> getMinimum() {
		return getCoordinates();
	}

	@Override
	public Tuple3<Double, Double, Double> getMaximum() {
		return getCoordinates();
	}

	private Tuple3<Double, Double, Double> getCoordinates() {
		if (coordinates == null)
			coordinates = Tuple3.from(x, y, z);

		return coordinates;
	}
}
