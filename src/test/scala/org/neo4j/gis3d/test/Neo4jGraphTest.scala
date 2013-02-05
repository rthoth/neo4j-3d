package org.neo4j.gis3d.test

import org.specs2.mutable.After
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory

trait Neo4jGraphTest {

	private object Graph {
		private val Path = "/tmp/neo4j-3d-test."
		private lazy val factory = new GraphDatabaseFactory()

		def apply(name: String) = synchronized {
			val path = Path + name
			factory.newEmbeddedDatabase(path)
		}
	}

	class Graph extends After {

		private var gds: Option[GraphDatabaseService] = None

		def graph = gds match {
			case Some(ds) => ds
			case None =>
				gds = Some(Graph(this.getClass.getName))
				gds.get
		}

		override def after = gds match {
			case Some(gds) => gds.shutdown()
			case None => ()
		}
	}

}