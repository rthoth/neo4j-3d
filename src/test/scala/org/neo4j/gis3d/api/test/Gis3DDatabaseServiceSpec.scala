package org.neo4j.gis3d.api.test

import scala.math.random

import org.neo4j.gis3d.Gis3DProperties
import org.neo4j.gis3d.api.Gis3DDatabaseService
import org.neo4j.gis3d.core.Point
import neo4j3d.api.test.Neo4jGraphTest
import org.specs2.mutable.Specification

class Gis3DDatabaseServiceSpec extends Specification with Neo4jGraphTest {

	"A Gis3D DatabaseService" should {

		"create or get a new layer" in new Graph {

			val gis3d = new Gis3DDatabaseService(graph)

			val layer = gis3d.getOrCreateLayer("A New Layer")

			layer must not beNull

		}

		"create a new 3D Point" in new Graph {
			val gis3d = new Gis3DDatabaseService(graph)

			val layer = gis3d.getOrCreateLayer("layer")

			layer must not beNull

			val nodes = for (i <- 1 to 10000) yield {
				val x, y, z = -50 + random * 100

				(layer.add(new Point(x, y, z)), x, y, z)
			}

			for (node <- nodes) {
				node._1.getProperty(Gis3DProperties.COORDINATES) match {
					case a: Array[Double] =>
						println("...")
					case _ =>
						println("epa")
				}
			}

		}
	}
}