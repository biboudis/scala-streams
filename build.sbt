import JmhKeys._

name := "scala-streams"

version := "1.0"

scalaVersion := "2.11.4"

resolvers ++= Seq(Resolver.sonatypeRepo("releases"), Resolver.sonatypeRepo("snapshots"))

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.11.6" % "test",
  "org.scala-lang" % "scala-reflect" % "2.11.4"
)

scalacOptions ++= Seq("-optimise",
		      "-Yclosure-elim",
		      "-Yinline")

javaOptions in run ++= Seq("-Xmx3G", "-Xms3G", "-XX:+TieredCompilation", "-XX:+UseParallelGC")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-maxSize", "5", "-minSuccessfulTests", "100", "-workers", "1", "-verbosity", "1")

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

jmhSettings
