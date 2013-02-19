package neo4j3d.api.test

import scala.math.random

import org.specs2.mutable.Specification

import grizzled.slf4j.Logging
import neo4j3d.api.SpatialProperties
import neo4j3d.core.SpatialGraphDatabaseServiceImpl
import neo4j3d.test.GeoTest
import neo4j3d.test.Neo4jGraphTest

class LayerTest extends Specification with Neo4jGraphTest with Logging with GeoTest {

	"SpatialGraphDatabaseService" should {
		"create a new layer" in new Graph {

			val sgds = new SpatialGraphDatabaseServiceImpl(graph)

			val layer = sgds.getOrCreateLayer("simple")

			val index = graph.index().forNodes("layers")

			val node = index.get(SpatialProperties.LAYER_NAME, "simple").getSingle

			layer.getRoot().getId() must equalTo(node.getId())

		}

		"add a new point" in new Graph {
			val sgds = new SpatialGraphDatabaseServiceImpl(graph)

			val layer = sgds getOrCreateLayer "another"

			val x, y, z = -50 + random * 100

			layer.add(point(x, y, z))

		}
	}

}