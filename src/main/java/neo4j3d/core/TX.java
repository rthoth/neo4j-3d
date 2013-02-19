package neo4j3d.core;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.TransactionFailureException;

public final class TX {

	public static <R> R tx(GraphDatabaseService gds, TXE<R> txe) {

		Transaction tx = null;
		try {
			tx = gds.beginTx();
			R value = txe.apply(gds);
			tx.success();
			return value;
		} catch (Exception e) {
			if (tx != null)
				tx.failure();
			throw new TransactionFailureException("TX", e);
		} finally {
			if (tx != null)
				tx.finish();
		}
	}
}
