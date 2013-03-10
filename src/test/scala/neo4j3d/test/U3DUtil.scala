package neo4j3d.test

import java.util.List
import scala.collection.JavaConversions.seqAsJavaList
import scala.math.pow
import scala.math.random
import scala.math.sqrt
import grizzled.slf4j.Logging
import neo4j3d.core.geom.Point
import neo4j3d.core.BBox

trait U3DUtil extends Logging {

	implicit def seqTuple2ListPoint(seqIn: Seq[(Double, Double, Double)]): List[Point] = {
		for (p <- seqIn) yield new Point(p._1, p._2, p._3)
	}

	implicit def seqTuple2ListBBox(seqIn: Seq[(Double, Double, Double)]): List[BBox] = {
		for (p <- seqIn) yield new Point(p._1, p._2, p._3).asInstanceOf[BBox]
	}

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
}