package neo4j3d.index.rtree;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.List;

import neo4j3d.Tuple3;
import neo4j3d.geom.BBox;
import neo4j3d.geom.Point;
import neo4j3d.index.Cluster;

import com.google.common.collect.Lists;

public class ClusterImpl implements Cluster {

	private Point center;
	private List<BBox> children = null;
	private Tuple3<Double, Double, Double> max;
	private Tuple3<Double, Double, Double> min;

	public ClusterImpl(BBox bbox) {
		getChidren().add(bbox);
		max = bbox.getMaximum();
		min = bbox.getMinimum();
		center = bbox.getCenter();
	}

	@Override
	public boolean add(BBox object) {
		getChidren().add(object);

		Tuple3<Double, Double, Double> omax = object.getMaximum();
		Tuple3<Double, Double, Double> omin = object.getMinimum();

		double xmax = max(max.a, omax.a);
		double ymax = max(max.b, omax.b);
		double zmax = max(max.c, omax.c);

		double xmin = min(min.a, omin.a);
		double ymin = min(min.b, omin.b);
		double zmin = min(min.c, omin.c);

		boolean update = false;

		if (!max.equals(xmax, ymax, zmax)) {
			update = true;
			max = Tuple3.from(xmax, ymax, zmax);
		}

		if (!min.equals(xmin, ymin, zmin)) {
			update = true;
			min = Tuple3.from(xmin, ymin, zmin);
		}

		return update;
	}

	@Override
	public Point getCenter() {
		return center;
	}

	private List<BBox> getChidren() {
		if (children != null)
			return children;

		return children = Lists.newLinkedList();
	}

	@Override
	public Tuple3<Double, Double, Double> getMaximum() {
		return max;
	}

	@Override
	public Tuple3<Double, Double, Double> getMinimum() {
		return min;
	}

	@Override
	public String toString() {
		return "Cluster(size:" + getChidren().size() + ", max:" + max + ", min:"
				+ min + ")";
	}

}
