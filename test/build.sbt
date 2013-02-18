name := "neo4j-3d-test"

version := "1.0-ALPHA"

scalaVersion := "2.10.0"

sbtVersion := "0.12.2"

libraryDependencies ++= Seq(
	"org.neo4j" % "server-api" % "1.9.M04",
	"org.specs2" %% "specs2" % "1.13",
	"org.clapper" % "grizzled-slf4j_2.10" % "1.0.1",
	"ch.qos.logback" % "logback-classic" % "1.0.9"
)