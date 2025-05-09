name         := "Molecule 3D"
organization := "scalafx.org"
version      := "0.2.6"

scalaVersion := "2.13.16"

resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "24.0.0-R35"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
