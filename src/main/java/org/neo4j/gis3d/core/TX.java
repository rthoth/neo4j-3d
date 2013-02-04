package org.neo4j.gis3d.core;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

public class TX {

	private TX() {

	}

	public static <R> R tx(GraphDatabaseService gds, TXE<R> txe) {
		Transaction tx = gds.beginTx();
		try {
			R r = txe.apply(gds);
			tx.success();
			return r;
		} finally {
			tx.finish();
		}
	}
}
