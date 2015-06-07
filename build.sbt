name := "scala-streams"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(Resolver.sonatypeRepo("releases"), 
		  Resolver.sonatypeRepo("snapshots"))

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.11.6" % "test",
  "org.scala-lang" % "scala-reflect" % "2.11.4",
  "org.scala-miniboxing.plugins" %% "miniboxing-runtime" % "0.4-M4"
)

scalacOptions ++= Seq("-optimise",
		      "-Yclosure-elim",
		      "-Yinline",
		      "-Yinline-warnings")

enablePlugins(JmhPlugin)

javaOptions in run ++= Seq("-Xms2g", "-Xmx2g", "-Xss4m",
			   "-XX:+CMSClassUnloadingEnabled",
			   "-XX:MaxPermSize=512M",
			   "-XX:ReservedCodeCacheSize=256m", "-XX:PermSize=256m",
			   "-XX:+TieredCompilation", "-XX:+UseNUMA")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, 
				      "-maxSize", "5", 
				      "-minSuccessfulTests", "100", 
				      "-workers", "1", 
				      "-verbosity", "1")

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }


// Enable miniboxing
addCompilerPlugin("org.scala-miniboxing.plugins" %% "miniboxing-plugin" % "0.4-M4")

// Miniboxed
// scalacOptions ++= Seq("-P:minibox:mark-all", "-P:minibox:Ykeep-functionX-values")

// Miniboxed + functions
scalacOptions ++= Seq("-P:minibox:mark-all")
