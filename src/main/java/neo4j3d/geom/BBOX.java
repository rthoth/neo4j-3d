package neo4j3d.geom;

public class BBOX {

	@SuppressWarnings("unused")
	private double[] bbox;

	public BBOX(double x0, double y0, double z0, double x1, double y1, double z1) {
		this.bbox = new double[] { x0, y0, z0, x1, y1, z1 };
	}
}
