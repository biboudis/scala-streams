name := "scala-streams"

version := "1.0"

scalaVersion := "2.10.4"

resolvers ++= Seq(Resolver.sonatypeRepo("releases"), Resolver.sonatypeRepo("snapshots"))

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.11.6" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scala-lang" % "scala-reflect" % "2.10.4"
)

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-maxSize", "5", "-minSuccessfulTests", "100", "-workers", "1", "-verbosity", "1")

jmhSettings
