// Name of the project
name := "Hello SBT"

// Project version
version := "1.0.5"

// Version of Scala used by the project
scalaVersion := "2.11.8"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.92-R10"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
