package neo4j3d.api.test

import scala.math.random

import org.neo4j.graphdb.NotFoundException
import org.neo4j.graphdb.Path
import org.neo4j.graphdb.traversal.Evaluation
import org.neo4j.graphdb.traversal.Evaluator
import org.neo4j.kernel.Traversal
import org.specs2.mutable.Specification

import grizzled.slf4j.Logging
import neo4j3d.api.SpatialProperties
import neo4j3d.core.SpatialGraphDatabaseServiceImpl
import neo4j3d.test.GeoTest
import neo4j3d.test.Neo4jGraphTest
import scala.collection.JavaConversions._

class LayerTest extends Specification with Neo4jGraphTest with Logging with GeoTest {

	"SpatialGraphDatabaseService" should {
		"create a new layer" in new Graph {

			val sgds = new SpatialGraphDatabaseServiceImpl(graph)

			val layer = sgds.getOrCreateLayer("simple", Map[String, Object]())

			val index = graph.index().forNodes("layers")

			val node = index.get(SpatialProperties.LAYER_NAME, "simple").getSingle

			layer.getRoot().getId() must equalTo(node.getId())

		}

		"add a new point" in new Graph {
			val sgds = new SpatialGraphDatabaseServiceImpl(graph)

			val layer = sgds getOrCreateLayer ("another", Map[String, Object]("limit" -> 10.asInstanceOf[Integer], "extent" -> Array[Double](-50, -50, -50, 50, 50, 50)))

			val x, y, z = -50 + random * 100

			val node = graph.createNode()

			layer.add(node, point(x, y, z))

			val traversal = Traversal.description().breadthFirst().evaluator(new Evaluator {

				override def evaluate(x: Path): Evaluation = try {
					val n = x.endNode
					if (n.getProperty(SpatialProperties.GEOM_TYPE) != "point") {
						(n.getProperty("x"), n.getProperty("y"), n.getProperty("z")) match {
							case (x, y, z) => Evaluation.INCLUDE_AND_PRUNE
							case _ => Evaluation.EXCLUDE_AND_CONTINUE
						}
					} else
						Evaluation.EXCLUDE_AND_CONTINUE
				} catch {
					case e: NotFoundException => Evaluation.EXCLUDE_AND_CONTINUE
				}

			})

			val i = traversal.traverse(layer.getRoot()).iterator

			i.hasNext must beTrue

		}
	}

}