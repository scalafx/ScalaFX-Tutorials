name := "SpreadsheetView"
scalaVersion := "2.13.8"

libraryDependencies += "org.scalafx" %% "scalafx" % "17.0.1-R26"
libraryDependencies += "org.controlsfx" % "controlsfx" % "11.1.1"

resolvers += Resolver.mavenLocal

// Prevent startup bug in JavaFX
fork := true
// Tell Javac and scalac to build for jvm 1.8
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
scalacOptions += "-target:jvm-1.8"
scalacOptions += "-feature"

// Add OS specific JavaFX dependencies
val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName        = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "18.0.1" classifier osName)

resolvers += Opts.resolver.sonatypeSnapshots
