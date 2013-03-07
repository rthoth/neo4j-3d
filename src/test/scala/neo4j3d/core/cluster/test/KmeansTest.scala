package neo4j3d.core.cluster.test

import scala.collection.JavaConversions.seqAsJavaList
import scala.collection.Seq.apply
import scala.math.random

import org.specs2.mutable.Specification

import neo4j3d.core.cluster.Clusterer
import neo4j3d.core.cluster.Kmeans
import neo4j3d.core.geom.Point

class KmeansTest extends Specification {

	"Kmeans" should {
		"create 5 cluster from 10k points" in {

			val centers = Seq[(Double, Double, Double)]((0, 0, 0), (2, 2, -2), (-2, -2, -2), (0, 0, 3), (0, 0, -3))

			val points = for (i <- 0 until 10000) yield {
				val center = centers((random * centers.size).intValue)
				new Point(center._1 - 1 + random * 2, center._2 - 1 + random * 2, center._3 - 1 + random * 2)
			}

			val kmeans: Clusterer = new Kmeans(50)

			val clusters = kmeans.cluster(points)

			clusters must not beNull

			clusters.size() must equalTo(5)

		}
	}
}