name := "neo4j-3d"

version := "1.0-ALPHA"

scalaVersion := "2.10.0"

sbtVersion := "0.12.2"


libraryDependencies ++= Seq(
	"org.neo4j" % "neo4j" % "1.8.1",
	"org.neo4j" % "server-api" % "1.8.1",
	"com.vividsolutions" % "jts" % "1.13",
	"org.specs2" %% "specs2" % "1.13" % "test"
)

resolvers ++= Seq(
	"sonatype releases" at "http://oss.sonatype.org/content/repositories/releases"
)

EclipseKeys.withSource := true

EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE17)