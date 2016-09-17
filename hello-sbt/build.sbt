// Name of the project
name := "Hello SBT"

// Project version
version := "1.0.6"

// Version of Scala used by the project
scalaVersion := "2.11.8"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.102-R11"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
