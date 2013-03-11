package neo4j3d.core.cluster.test
import scala.math.random
import org.specs2.mutable._
import neo4j3d.core.geom.Point
import neo4j3d.core.cluster.ClusterSeeder
import neo4j3d.test.U3DUtil
import scala.collection.JavaConversions._

class SeederMaxStraightTest extends Specification with U3DUtil {

	"Seeder Max Straight Test".title

	"Spected find 2 points" should {

		"(-50,-50,-50) random(10k, [(-45,-45,-45),...(45,45,45)]) (50,50,50)" in {

			val (fixed, factor) = (1, 1)

			val i1 = fixed + (random * factor).toInt

			val seq1 = for (i <- 0 until i1) yield {
				val x, y, z = (random * 90) - 45
				new Point(x, y, z)
			}

			val i2 = fixed + (random * factor).toInt

			val seq2 = for (i <- 0 until i2) yield {
				val x, y, z = (random * 90) - 45
				new Point(x, y, z)
			}

			val i3 = fixed + (random * factor).toInt

			val seq3 = for (i <- 0 until i3) yield {
				val x, y, z = (random * 80) - 40
				new Point(x, y, z)
			}

			val expectedFirst = new Point(-50, -50, -50);
			val expectedSecond = new Point(50, 50, 50);

			val seq = seq1 ++ Seq(expectedFirst) ++ seq2 ++ Seq(expectedSecond) ++ seq3

			val result = ClusterSeeder.apply(seq, 500);

			result should not beNull

			val first = result(0);
			var second = result(1);

			first should equalTo(expectedFirst)

			second should equalTo(expectedSecond)

			true
		}
	}
}