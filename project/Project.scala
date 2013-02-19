import sbt._
import Keys._


object Neo4j3D extends Build {

	lazy val test = Project(id = "neo4j3d-test",
		base = file("test"))

	lazy val root = Project(id = "neo4j3d-core", 
		base = file(".")) aggregate (test) dependsOn (test)
}