package org.neo4j.gis3d.core;

import java.io.Serializable;

public class Point extends Geometry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double x = Double.NaN;
	private double y = Double.NaN;
	private double z = Double.NaN;

	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Boundary getBoundary() {
		// TODO Auto-generated method stub
		return null;
	}
}
