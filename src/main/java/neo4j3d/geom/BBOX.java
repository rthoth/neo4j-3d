package neo4j3d.geom;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.Arrays;

public class BBOX {

	public static BBOX from(double[] bbox) {
		return new BBOX(bbox[0], bbox[1], bbox[2], bbox[3], bbox[4], bbox[5]);
	}

	private double[] bbox;
	private double toWeight = Double.NEGATIVE_INFINITY;
	private BBOX toTarget;
	@SuppressWarnings("unused")
	private BBOX fromTarget;
	@SuppressWarnings("unused")
	private double fromWeight;

	public BBOX(double x0, double y0, double z0, double x1, double y1, double z1) {
		this.bbox = new double[] { x0, y0, z0, x1, y1, z1 };
	}

	public void disconnect() {
		toWeight = Double.NEGATIVE_INFINITY;
		toTarget = null;
	}

	public double distanceOf(BBOX other) {
		double[] myCentroid = getCentroid();
		double[] otherCentroid = other.getCentroid();
		return sqrt(pow(myCentroid[0] - otherCentroid[0], 2)
				+ pow(myCentroid[1] - otherCentroid[1], 2)
				+ pow(myCentroid[2] - otherCentroid[2], 2));
	}

	private void from(BBOX other, double weight) {
		fromTarget = other;
		fromWeight = weight;
	}

	public double[] getCentroid() {
		return new double[] { (bbox[0] + bbox[3]) / 2, (bbox[1] + bbox[4]) / 2,
				(bbox[3] + bbox[5]) / 2 };
	}

	public double getToWeight() {
		return toWeight;
	}

	public void to(BBOX other, double weight) {
		toTarget = other;
		this.toWeight = weight;
		other.from(this, weight);
	}

	public double[] toArray() {
		return Arrays.copyOf(bbox, 6);
	}

	public BBOX to() {
		return toTarget;
	}
}
