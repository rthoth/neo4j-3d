package neo4j3d.index.rtree.test

import org.specs2.mutable.Specification
import scala.collection.JavaConversions._
import neo4j3d.test.TestHelper
import neo4j3d.index.rtree.Clusterer

class ClustererTest extends Specification with TestHelper {

	def testSize = 1000000

	val mkpoints = points(testSize, 20, 20, 20)_

	val clusterer = new Clusterer(500)

	"Clusterer Tests" title

	"Clusterer" should {

		s"Expected 3 clusters in $testSize objects" in {
			val centers = Seq[(Double, Double, Double)]((0, 0, 0), (40, 40, 0), (20, 20, 40))
			val p = mkpoints(centers)

			val clusters = clusterer(p)

			clusters must not beNull

			clusters.size must equalTo(centers.size)
		}

	}

}