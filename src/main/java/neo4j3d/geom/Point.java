package neo4j3d.geom;

public class Point extends Geometry {

	private double x;
	private double y;
	private double z;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	@Override
	public BBOX getBBOX() {
		return null;
	}
}
