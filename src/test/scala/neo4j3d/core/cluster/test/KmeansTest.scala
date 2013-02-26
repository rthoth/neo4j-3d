package neo4j3d.core.cluster.test

import scala.collection.JavaConversions._
import scala.math.random
import org.specs2.mutable.Specification
import neo4j3d.core.cluster.Kmeans
import neo4j3d.geom.Geometry
import neo4j3d.geom.Point
import neo4j3d.geom.BBOX
import neo4j3d.core.cluster.Clusterer

class KmeansTest extends Specification {

	"Kmens" should {

		"group 10k geometries in 5 groups" in {
			val groups = Seq[(Double, Double, Double)]((0, 0, 0), (2, 2, 2), (-2, 2, -2), (3, 3, 3), (-3, -3, 3))

			val points: Seq[BBOX] = for (i <- 0 until 10000) yield {
				val center = groups((random * groups.size).intValue)

				new Point(center._1 - 1 + random * 2, center._2 - 1 + random * 2, center._3 - 1 + random * 2).getBBOX
			}

			val clusterer: Clusterer = new Kmeans(50)
			val clusters = clusterer apply points

			clusters should not beNull

		}
	}
}