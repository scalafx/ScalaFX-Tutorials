name := "ScalaFXML Example"
organization := "scalafx.org"
version := "1.0.6"

scalaVersion := "2.13.8"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-Ymacro-annotations")

Compile / resourceDirectory := (Compile / scalaSource).value
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "18.0.1-R27",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"
  )

// Add OS specific JavaFX dependencies
libraryDependencies ++=
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "18.0.1")

resolvers += Opts.resolver.sonatypeSnapshots

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
