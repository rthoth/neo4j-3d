package neo4j3d.core.cluster;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import neo4j3d.core.BBox;
import neo4j3d.core.Tuple3;
import neo4j3d.core.geom.Point;

public class ClusterImpl implements Cluster {

	private BBox center;
	private Tuple3<Double, Double, Double> minimum;
	private Tuple3<Double, Double, Double> maximum;
	@SuppressWarnings("unused")
	private Cluster parent;

	public ClusterImpl(Point center) {
		this.center = center;
		minimum = center.getCenterCoordinates();
		maximum = minimum;
	}

	@Override
	public boolean add(BBox volume) {

		Tuple3<Double, Double, Double> otherMinimum = volume.minimum();
		Tuple3<Double, Double, Double> otherMaximum = volume.maximum();

		double xmin = min(minimum._1, otherMinimum._1);
		double ymin = min(minimum._2, otherMinimum._2);
		double zmin = min(minimum._3, otherMinimum._3);

		double xmax = max(maximum._1, otherMaximum._1);
		double ymax = max(maximum._2, otherMaximum._2);
		double zmax = max(maximum._3, otherMaximum._3);

		boolean changed = false;
		if (!minimum.equals(xmin, ymin, zmin)) {
			minimum = Tuple3.from(xmin, ymin, zmin);
			changed = true;
		}

		if (!maximum.equals(xmax, ymax, zmax)) {
			maximum = Tuple3.from(xmax, ymax, zmax);
			changed = true;
		}

		if (changed) {
			updateCenter();
		}

		// getObjects().add(volume);

		volume.setCluster(this);

		return changed;

	}

	@Override
	public double coverage() {
		return (maximum._1 - minimum._1) * (maximum._2 - minimum._2)
				* (maximum._3 - minimum._3);
	}

	@Override
	public double distanceOf(BBox other) {
		return center.distanceOf(other);
	}

	@Override
	public Tuple3<Double, Double, Double> getCenterCoordinates() {
		return center.getCenterCoordinates();
	}

	@Override
	public Point getCenter() {
		return center.getCenter();
	}

	@Override
	public Tuple3<Double, Double, Double> maximum() {
		return this.maximum;
	}

	@Override
	public Tuple3<Double, Double, Double> minimum() {
		return this.minimum;
	}

	@Override
	public double overlap(Cluster other) {

		Tuple3<Double, Double, Double> omin = other.minimum();
		Tuple3<Double, Double, Double> omax = other.maximum();

		double a = max(minimum._1, omin._1);
		double b = max(minimum._2, omin._2);
		double c = max(minimum._3, omin._3);

		double d = min(maximum._1, omax._1);
		double e = min(maximum._2, omax._2);
		double f = min(maximum._3, omax._3);

		if (a >= d || b >= e || c >= f)
			return 0;
		else
			return (d - a) * (e - b) * (f - c);
	}

	@Override
	public double shape() {
		return pow(
				((maximum._1 - minimum._1) + (maximum._2 - minimum._2) + (maximum._3 - minimum._3)) / 3,
				3);
	}

	private void updateCenter() {
		double x = (maximum._1 + minimum._2) / 2;
		double y = (maximum._2 + minimum._2) / 2;
		double z = (maximum._3 + minimum._3) / 2;
		center = new Point(x, y, z);
	}

	@Override
	public void setCluster(Cluster cluster) {
		this.parent = cluster;
	}

}
