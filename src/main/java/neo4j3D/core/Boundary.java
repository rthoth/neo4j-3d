package neo4j3D.core;

public class Boundary {

	private double[] boundary;

	/**
	 * 3D - X0,Y0,Z0,X1,Y1,Z1
	 * 
	 *  _______ 1 
	 * /_____ /|
	 * |     | |
	 * |     | | 
	 * |_____|/
	 * 0
	 * 
	 * @param boundary
	 */
	public Boundary(double[] boundary) {
		if (boundary != null && boundary.length == 6) {
			this.boundary = boundary;
		} else
			throw new IllegalArgumentException("Bounday must be double[6]");
	}

	public boolean within(double[] other) {
		if (other != null && other.length == 6) {
			if (other != boundary) {
				
			} else
				return true;
		}
		return false;
	}
}
