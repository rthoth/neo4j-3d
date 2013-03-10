package neo4j3d.core.cluster.test

import scala.collection.JavaConversions._
import scala.collection.Seq._
import org.specs2.mutable.Specification
import neo4j3d.core.cluster.Seeds
import neo4j3d.test.U3DUtil
import neo4j3d.core.cluster.StrictKmeans

class KmeansAlgoTest extends Specification with U3DUtil {

	"KmeansAlgo" should {

		skipAll

		"7 centers in 10k random points" in {
			val centers = Seq[(Double, Double, Double)](
				(0, 0, 0), (3, 3, 3), (-3, 3, 3),
				(-3, 3, -3), (-3, -3, 0), (3, -3, 0),
				(0, 0, 10))

			val vols = volumes(centers, 10000);
			val seeds = Seeds.apply(vols, centers.size);

			val clusters = StrictKmeans.apply(seeds, vols);

			clusters must not beNull

			clusters._2 must not beNull

			clusters._2.size must equalTo(centers.size)

			true
		}
	}

}