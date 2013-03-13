package neo4j3d.core.cluster.test

import scala.collection.JavaConversions._
import scala.collection.Seq.apply

import org.specs2.mutable.Specification

import neo4j3d.core.cluster.Clusterer
import neo4j3d.core.cluster.Kmeans
import neo4j3d.test.U3DUtil

class KmeansTest extends Specification with U3DUtil {

	"Kmeans" should {

		"create 5 cluster from 100k points" in {

			val centers = Seq[(Double, Double, Double)]((-50, 0, 0), (0, 50, 0), (50, 0, 0))

			val vols = volumes(centers, 10000);

			val kmeans: Clusterer = new Kmeans(100)

			val clusters = kmeans.cluster(vols)

			clusters must not beNull

			for (cl <- clusters)
				info(cl.getCenter())

			clusters.size() must equalTo(centers.size)

		}
	}
}