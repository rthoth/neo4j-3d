package neo4j3d.test

import scala.Some
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import grizzled.slf4j.Logging
import org.specs2.mutable.After
import org.neo4j.graphdb.Transaction
import scala.collection.mutable.Set

trait Neo4jGraphTest {

	private object Graph extends Logging {
		private val Path = "/tmp/neo4j-3d-test"
		private lazy val factory = new GraphDatabaseFactory()
		private lazy val graph = factory.newEmbeddedDatabase(Path)
		private val set = Set[After]()

		def apply(after: After) = synchronized {
			info("Loading Grahp Database Service")
			set += after
			graph
		}

		def shutdown(after: After) {
			if (set contains after) {
				info("-1 Graph Database Service")
				set -= after
				if (set.isEmpty)
					try {
						info("Graph Database Service shutdown")
						graph.shutdown()
					} catch {
						case e: Exception => e.printStackTrace()
					}
			}
		}

	}

	class Graph extends After {

		private var gds: Option[GraphDatabaseService] = None
		private var tx: Transaction = null

		def graph = gds match {
			case Some(ds) => ds
			case None =>
				gds = Some(Graph(this))
				tx = gds.get.beginTx();
				gds.get
		}

		override def after = gds match {
			case Some(gds) => {
				tx.finish()
				Graph shutdown this
			}
			case None => ()
		}
	}

}