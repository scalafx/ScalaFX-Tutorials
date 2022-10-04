name := "splash-demo"

organization := "org.scalafx"

version := "0.2.2"

scalaVersion := "2.13.9"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "18.0.1-R28"

resolvers ++= Opts.resolver.sonatypeOssSnapshots

// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
Compile / run / mainClass := Some("splash_demo.SplashDemoApp")
