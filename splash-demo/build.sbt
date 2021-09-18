name := "splash-demo"

organization := "org.scalafx"

version := "0.2.1"

scalaVersion := "2.13.6"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "16.0.0-R25"

// Add OS specific JavaFX dependencies
val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName        = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)

resolvers += Opts.resolver.sonatypeSnapshots

// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
Compile / run / mainClass := Some("splash_demo.SplashDemoApp")
