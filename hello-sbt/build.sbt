// Name of the project
name := "Hello SBT"

// Project version
version := "1.0.6"

// Version of Scala used by the project
scalaVersion := "2.13.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "12.0.2-R18"

// Add OS specific JavaFX dependencies
val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "12.0.2" classifier osName)


// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
