name         := "Molecule 3D"
organization := "scalafx.org"
version      := "0.2.6"

scalaVersion := "2.13.12"

resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "21.0.0-R32"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
