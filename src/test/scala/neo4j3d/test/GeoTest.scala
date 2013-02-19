package neo4j3d.test

import neo4j3d.geom.GeoFactory

trait GeoTest {

	lazy val geoFactory = new GeoFactory

	def point(x: Double, y: Double, z: Double) = geoFactory.point(x, y, z)
}