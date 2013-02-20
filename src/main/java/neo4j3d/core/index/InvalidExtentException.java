package neo4j3d.core.index;

public class InvalidExtentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidExtentException(String message) {
		super(message);
	}

	public InvalidExtentException(Throwable cause) {
		super(cause);
	}

}
