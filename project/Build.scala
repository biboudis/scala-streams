import sbt._
import Keys._
import pl.project13.scala.sbt._

object MyBuild extends Build {

  lazy val defaultSettings = Defaults.defaultSettings ++ Seq[Setting[_]](
    name := "scala-streams",
    version := "1.0",
    scalaVersion := "2.11.6",
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % "1.11.6" % "test",
      "org.scala-lang" % "scala-reflect" % "2.11.4",
      "com.github.biboudis" % "jmh-profilers" % "0.1.2"
    ),
    scalacOptions ++= Seq("-optimise",
			  "-Yclosure-elim",
			  "-Yinline",
			  "-Yinline-warnings"),
    ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },
    javaOptions in run ++= Seq("-Xms2G"),
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, 
					  "-maxSize", "5", 
					  "-minSuccessfulTests", "100", 
					  "-workers", "1", 
					  "-verbosity", "1")
  )

  lazy val miniboxingSettings = Seq[Setting[_]](
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    libraryDependencies += "org.scala-miniboxing.plugins" %% "miniboxing-runtime" % "0.4-SNAPSHOT" changing(),
    addCompilerPlugin("org.scala-miniboxing.plugins" %% "miniboxing-plugin" % "0.4-SNAPSHOT" changing()),
    scalacOptions ++= Seq()
  )

  lazy val miniboxed = Project(
    "streams-miniboxed",
    file("miniboxed"),
    settings = defaultSettings ++ miniboxingSettings
  ).enablePlugins(JmhPlugin)

  lazy val erased = Project(
    "streams-erased",
    file("erased"),
    settings = defaultSettings
  ).enablePlugins(JmhPlugin)
}
