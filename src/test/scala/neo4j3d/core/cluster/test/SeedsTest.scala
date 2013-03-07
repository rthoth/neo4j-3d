package neo4j3d.core.cluster.test

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.seqAsJavaList
import scala.collection.Seq.apply
import scala.math.pow
import scala.math.random
import scala.math.sqrt

import org.specs2.mutable.Specification

import grizzled.slf4j.Logging
import neo4j3d.core.cluster.Seeds
import neo4j3d.core.geom.Point

class SeedsTest extends Specification with Logging {

	val _d = 1
	val _2d = 2 * _d
	val _3dl = sqrt(3) * _d

	def volumes(centers: Seq[(Double, Double, Double)], size: Int): Seq[Point] = {
		val vols = for (i <- 0 until size) yield {
			val centerIndex = (random * centers.size).intValue
			val center = centers(centerIndex)
			new Point(center._1 - _d + random * _2d, center._2 - _d + random * _2d, center._3 - _d + random * _2d);
		}

		info("Created " + size + " random points")
		vols
	}

	def cdistance(p: Point, q: (Double, Double, Double)): Double =
		sqrt(pow(q._1 - p.x, 2) + pow(q._2 - p.y, 2) + pow(q._3 - p.z, 2))

	def found(point: Point, seq: Seq[(Double, Double, Double)]): Boolean = if (seq.isEmpty) false else {
		val distance = cdistance(point, seq.head)
		if (distance <= _3dl)
			true
		else
			found(point, seq.tail)
	}

	"Seed" should {

		"5 initial groups in 10k points" in {
			val centers = Seq[(Double, Double, Double)]((0, 0, 0), (2, 2, 2), (-2, -2, -2), (-2, 2, 2), (0, 0, 2))
			val clusters = Seeds.apply(volumes(centers, 10000), centers.size);

			clusters.size should equalTo(centers.size)
		}

		"10 initial groups in 10k points" in {
			val centers = Seq[(Double, Double, Double)](
				(2, 2, 0), (0, 0, 2), (-2, 2, 0), (0, 0, 0), (0, 0, -2),
				(2, 2, -2), (0, 0, 4), (-2, 2, -2), (0, 0, 5), (0, 0, 2))

			val clusters = Seeds.apply(volumes(centers, 10000), centers.size)

			clusters.size should equalTo(centers.size)
		}

		"7 initial groups nearest 3 spected in 100k points" in {

			val centers = Seq[(Double, Double, Double)](
				(0, 0, 0), (2, 2, 2), (-2, 2, 0), (0, 0, 2),
				(0, 2, 2), (4, 4, 4), (0, 0, -4), (-4, 4, 0),
				(0, 0, 10))

			val clusters = Seeds.apply(volumes(centers, 100000), centers.size)

			clusters.size should equalTo(centers.size)

			for (cluster <- clusters)
				found(cluster, centers) should beTrue
		}
	}
}