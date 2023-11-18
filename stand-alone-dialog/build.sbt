name         := "Stand-Alone Dialog"
organization := "scalafx.org"
version      := "1.0.8"

scalaVersion := "2.13.12"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32"

resolvers ++= Opts.resolver.sonatypeOssSnapshots

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true

