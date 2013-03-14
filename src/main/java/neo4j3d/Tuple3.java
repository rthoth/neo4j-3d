package neo4j3d;

public class Tuple3<A, B, C> {

	public static <A, B, C> Tuple3<A, B, C> from(A a, B b, C c) {
		return new Tuple3<A, B, C>(a, b, c);
	}

	public final A a;
	public final B b;
	public final C c;

	private Tuple3(A a, B b, C c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public boolean equals(A ap, B bp, C cp) {
		return a.equals(ap) && b.equals(bp) && c.equals(cp);
	}

	@Override
	public String toString() {
		return "(" + a + ", " + b + ", " + c + ')';
	}

}
