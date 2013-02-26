package neo4j3d.geom;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Point extends Geometry {

	public static class PBBOX extends BBOX {
		private Point point;

		public PBBOX(Point point) {
			super(point.x, point.y, point.z, point.x, point.y, point.z);
			this.point = point;
		}

		@Override
		public double distanceOf(BBOX other) {
			double[] centroid = other.getCentroid();
			return sqrt(pow(point.x - centroid[0], 2) + pow(point.y - centroid[1], 2)
					+ pow(point.z - centroid[2], 2));
		}
	}

	private double x;
	private double y;
	private double z;
	private BBOX bbox;

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
		if (bbox != null)
			return bbox;
		else {
			bbox = new PBBOX(this);
			return bbox;
		}
	}
}
