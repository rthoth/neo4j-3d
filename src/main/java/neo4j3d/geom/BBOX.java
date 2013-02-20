package neo4j3d.geom;

import java.util.Arrays;

public class BBOX {

	private double[] bbox;

	public BBOX(double x0, double y0, double z0, double x1, double y1, double z1) {
		this.bbox = new double[] { x0, y0, z0, x1, y1, z1 };
	}

	public static BBOX from(double[] bbox) {
		return new BBOX(bbox[0], bbox[1], bbox[2], bbox[3], bbox[4], bbox[5]);
	}

	public double[] toArray() {
		return Arrays.copyOf(bbox, 6);
	}
}
