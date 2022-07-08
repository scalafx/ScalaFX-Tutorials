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
libraryDependencies ++=
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "18.0.1")

resolvers += Opts.resolver.sonatypeSnapshots
