package neo4j3d.core;

import static java.lang.Math.sqrt;
import neo4j3d.core.geom.Point;

public class Math {

	public static class Straight {

		public final double px, py, pz, i, j, k;

		public Straight(double px, double py, double pz, double i, double j,
				double k) {
			this.px = px;
			this.py = py;
			this.pz = pz;
			this.i = i;
			this.j = j;
			this.k = k;
		}

		public Point center() {
			return new Point(px + i / 2, py + j / 2, pz + k / 2);
		}

	}

	public static Straight straight(Point p, Point q) {
		return new Straight(p.x, p.y, p.z, q.x - p.x, q.y - p.y, q.z - p.z);
	}

	public static double distance(Point point, Straight straight) {

		double x = straight.px - point.x;
		double y = straight.py - point.y;
		double z = straight.pz - point.z;

		double vx = straight.i;
		double vy = straight.j;
		double vz = straight.k;

		double i = (y * vz) - (z * vy);
		double j = (z * vx) - (x * vz);
		double k = (x * vy) - (y * vx);

		double den = i * i + j * j + k * k;
		double num = vx * vx + vy * vy + vz * vz;

		return sqrt(den / num);
	}

}
