// @formatter:off

name := "ScalaFXML Example"
organization := "scalafx.org"
version := "1.0.6"

scalaVersion := "2.13.6"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-Ymacro-annotations")

resourceDirectory in Compile := (scalaSource in Compile).value
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx"             % "16.0.0-R25",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"
)

// Add OS specific JavaFX dependencies
val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)

resolvers += Opts.resolver.sonatypeSnapshots

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true


