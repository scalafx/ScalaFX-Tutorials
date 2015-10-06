name := "Stand-Alone Dialog"

version := "1.0.3"

scalaVersion := "2.11.7"

// Add managed dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.60-R9"

// Fork a new JVM for 'run' and 'test:run'
fork := true

//fork in console := true