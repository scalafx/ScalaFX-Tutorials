// Name of the project
name := "Hello SBT"

// Project version
version := "1.0.7"

// Version of Scala used by the project
scalaVersion := "2.13.12"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32"

resolvers ++= Opts.resolver.sonatypeOssSnapshots

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
