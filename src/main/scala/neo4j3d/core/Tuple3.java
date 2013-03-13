package neo4j3d.core;

public class Tuple3<T1, T2, T3> {
	public final T1 _1;

	public final T2 _2;

	public final T3 _3;

	private Tuple3(T1 _1, T2 _2, T3 _3) {
		this._1 = _1;
		this._2 = _2;
		this._3 = _3;
	}

	public boolean equals(T1 _1, T2 _2, T3 _3) {
		return this._1.equals(_1) && this._2.equals(_2) && this._3.equals(_3);
	}

	@Override
	public String toString() {
		return "(" + _1.toString() + ", " + _2.toString() + ", " + _3.toString()
				+ ")";
	}

	public static <T1, T2, T3> Tuple3<T1, T2, T3> from(T1 _1, T2 _2, T3 _3) {
		return new Tuple3<T1, T2, T3>(_1, _2, _3);
	}
}
