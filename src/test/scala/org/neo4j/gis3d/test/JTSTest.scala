package org.neo4j.gis3d.test

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.PrecisionModel

trait JTSTest {

	lazy val geoFactory = new GeometryFactory(new PrecisionModel())

	def Point(x: Double, y: Double, z: Double) = geoFactory.createPoint(new Coordinate(x, y, z))
}