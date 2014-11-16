name := "scala-streams"

version := "1.0"

scalaVersion := "2.11.4"

resolvers ++= Seq(Resolver.sonatypeRepo("releases"), Resolver.sonatypeRepo("snapshots"))

libraryDependencies ++= Seq(
  "org.scala-miniboxing.plugins" %% "miniboxing-runtime" % "0.4-SNAPSHOT",
  "org.scalacheck" %% "scalacheck" % "1.11.6" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scala-lang" % "scala-reflect" % "2.11.4"
)

addCompilerPlugin("org.scala-miniboxing.plugins" %%
                  "miniboxing-plugin" % "0.4-SNAPSHOT")

scalacOptions += "-optimize"

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-maxSize", "5", "-minSuccessfulTests", "100", "-workers", "1", "-verbosity", "1")

jmhSettings
