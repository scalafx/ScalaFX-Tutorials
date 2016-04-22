name := "Stand-Alone Dialog"

version := "1.0.4"

scalaVersion := "2.11.8"

// Add managed dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.72-R10-SNAPSHOT"

// Fork a new JVM for 'run' and 'test:run'
fork := true

//fork in console := true