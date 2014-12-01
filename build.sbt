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

// javaOptions in run ++= Seq("-Xmx3G", "-Xms3G", "-XX:+TieredCompilation", "-XX:+UseParallelGC")

javaOptions in run ++= Seq("-Xms3G")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-maxSize", "5", "-minSuccessfulTests", "100", "-workers", "1", "-verbosity", "1")

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

jmhSettings

// miniboxing:

libraryDependencies += "org.scala-miniboxing.plugins" %% "miniboxing-runtime" % "0.4-SNAPSHOT" changing()

addCompilerPlugin("org.scala-miniboxing.plugins" %% "miniboxing-plugin" % "0.4-SNAPSHOT" changing())

scalacOptions ++= Seq("-P:minibox:warn", "-P:minibox:mark-all", "-P:minibox:Yrewire-functionX-application")

// Vlad: I use these for running locally:
// libraryDependencies += "org.scala-miniboxing.plugins" %% "miniboxing-runtime" % "0.4-SNAPSHOT" from "file:///mnt/data1/Work/Workspace/dev/miniboxing-plugin/components/runtime/target/scala-2.11/miniboxing-runtime_2.11-0.4-SNAPSHOT.jar"
//scalacOptions += "-Xplugin:/mnt/data1/Work/Workspace/dev/miniboxing-plugin/components/plugin/target/scala-2.11/miniboxing-plugin_2.11-0.4-SNAPSHOT.jar"
