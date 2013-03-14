package neo4j3d.test

import scala.collection.JavaConversions.asJavaIterable
import scala.math.random

import com.google.common.collect.ImmutableList

import grizzled.slf4j.Logging
import neo4j3d.geom.Point

trait TestHelper extends Logging {

	implicit def seq2GList[A](seq: Seq[A]): ImmutableList[A] = ImmutableList.copyOf(asJavaIterable(seq))

	def points(n: Int, dx: Double, dy: Double, dz: Double)(centers: Seq[(Double, Double, Double)]): Seq[Point] = {
		val ddx = dx * 2; val ddy = dy * 2; val ddz = dz * 2
		for (i <- 0 until n) yield {
			val index = (centers.size - 1) * random
			val c = centers(index.intValue)
			Point.from(c._1 + random * ddx - dx, c._2 + random * ddy - dy, c._3 + random * ddz - dz)
		}
	}
}