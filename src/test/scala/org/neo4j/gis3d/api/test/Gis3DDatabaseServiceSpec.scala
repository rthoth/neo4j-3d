package org.neo4j.gis3d.api.test

import org.neo4j.gis3d.test.JTSTest
import org.neo4j.gis3d.test.Neo4jGraphTest
import org.specs2.mutable.Specification
import org.neo4j.gis3d.api.Gis3DDatabaseService
import scala.math.random

class Gis3DDatabaseServiceSpec extends Specification with Neo4jGraphTest with JTSTest {

	"A Gis3D DatabaseService" should {

		"create or get a new layer" in new Graph {

			val gis3dgds = new Gis3DDatabaseService(graph)

			gis3dgds.getOrCreateLayer("A New Layer") must not beNull

			println("#1")
		}

		"create a new 3D Point" in new Graph {
			val x, y, z = -50 + random * 100

			val gis3dgds = new Gis3DDatabaseService(graph)
			val layer = gis3dgds.getOrCreateLayer("A test layer #1")

			val node = layer.add(Point(x, y, z))
			println("#2")
		}
	}
}