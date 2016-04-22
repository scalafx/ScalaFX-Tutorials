name := "Stand-Alone Dialog"

version := "1.0.5"

scalaVersion := "2.11.8"

// Add managed dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.92-R10"

// Fork a new JVM for 'run' and 'test:run'
fork := true

//fork in console := true