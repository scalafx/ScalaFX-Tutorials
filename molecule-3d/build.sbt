// @formatter:off

name         := "Molecule 3D"
organization := "scalafx.org"
version      := "0.2.4"

scalaVersion := "2.12.1"

resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true

shellPrompt := { state => System.getProperty("user.name") + s":${name.value}> " }
