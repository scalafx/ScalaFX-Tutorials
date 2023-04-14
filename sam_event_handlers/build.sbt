name         := "SAM Event Handlers"
organization := "ScalaFX.org"
version      := "0.1.3"

scalaVersion := "2.13.10"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31"

resolvers ++= Opts.resolver.sonatypeOssSnapshots

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
