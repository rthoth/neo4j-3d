package org.neo4j.gis3d.test

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.specs2._
import org.specs2.specification.FragmentsBuilder.ImplicitParam

trait Neo4jGraphTest {

	var graph: GraphDatabaseService = null

	class Graph(name: String) extends mutable.After {

		private lazy val Path = "/tmp/neo4j-3d-test-" + name
		private lazy val factory = new GraphDatabaseFactory()
		private var gds: Option[GraphDatabaseService] = None

		override def after {
			if (!gds.isEmpty)
				gds.get.shutdown()
		}

		def graph: GraphDatabaseService = gds match {
			case Some(ds) => ds
			case None =>
				gds = Some(factory.newEmbeddedDatabase(Path))
				gds.get
		}
	}

	def openGraph(name: String) = {
		graph = new Graph(name).graph
	}

	def closeGraph = {
		if (graph != null)
			graph.shutdown();
	}
}