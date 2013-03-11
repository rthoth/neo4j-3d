package neo4j3d.core.cluster.test

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.seqAsJavaList
import scala.collection.Seq.apply
import org.specs2.mutable.Specification
import neo4j3d.test.U3DUtil
import neo4j3d.core.cluster.ClusterSeeder

class SeederTest extends Specification with U3DUtil {

	"Kmeans Seeder Tests" title

	"Seeder" should {

		skipAll

		"5 initial groups in 1k points" in {
			val centers = Seq[(Double, Double, Double)]((0, 0, 0), (2, 2, 2), (-2, -2, -2), (-2, 2, 2), (0, 0, 2))
			val clusters = ClusterSeeder.apply(volumes(centers, 1000), centers.size);

			clusters.size should equalTo(centers.size)
		}

		"10 initial groups in 10k points" in {
			val centers = Seq[(Double, Double, Double)](
				(2, 2, 0), (0, 0, 2), (-2, 2, 0), (0, 0, 0), (0, 0, -2),
				(2, 2, -2), (0, 0, 4), (-2, 2, -2), (0, 0, 5), (0, 0, 2))

			val clusters = ClusterSeeder.apply(volumes(centers, 10000), centers.size)

			clusters.size should equalTo(centers.size)
		}

		"7 initial groups nearest 7 spected in 100k points" in {

			val centers = Seq[(Double, Double, Double)](
				(0, 0, 0), (2, 2, 2), (-2, 2, 0), (0, 0, 2),
				(0, 2, 2), (4, 4, 4), (0, 0, -4), (-4, 4, 0),
				(0, 0, 10))

			val clusters = ClusterSeeder.apply(volumes(centers, 100000), centers.size)

			clusters.size should equalTo(centers.size)

			for (cluster <- clusters)
				found(cluster, centers) should beTrue
		}

		"13 initial groups nearest 13 spected in 1m points" in {
			val centers = Seq[(Double, Double, Double)](
				(0, 0, 0), (2, 2, 2), (0, 2, 2), (0, 4, -2),
				(-2, 2, 2), (-4, 2, -2), (-2, 2, -2), (0, 2, -2),
				(-2, -2, 0), (-2, -4, -2), (-4, -2, 0), (-2, -2, -2),
				(0, 0, -10))

			val clusters = ClusterSeeder.apply(volumes(centers, 1000000), centers.size)

			clusters.size should equalTo(centers.size)
		}
	}
}