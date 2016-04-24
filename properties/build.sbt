name := "Properties"

organization := "scalafx.org"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.92-R10",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

shellPrompt := { state => System.getProperty("user.name") + "> " }

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
