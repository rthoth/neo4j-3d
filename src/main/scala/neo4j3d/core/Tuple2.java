package neo4j3d.core;

public class Tuple2<T1, T2> {

	public final T1 _1;
	public final T2 _2;

	private Tuple2(T1 _1, T2 _2) {
		this._1 = _1;
		this._2 = _2;
	}

	@Override
	public String toString() {
		return "(" + _1.toString() + ", " + _2.toString() + ")";
	}

	public static <T1, T2> Tuple2<T1, T2> from(T1 _1, T2 _2) {
		return new Tuple2<T1, T2>(_1, _2);
	}

}
