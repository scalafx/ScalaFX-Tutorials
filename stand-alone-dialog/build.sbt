// @formatter:off

name         := "Stand-Alone Dialog"
organization := "scalafx.org"
version      := "1.0.7"

scalaVersion := "2.12.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true

//fork in console := true

shellPrompt := { state => System.getProperty("user.name") + s":${name.value}> " }