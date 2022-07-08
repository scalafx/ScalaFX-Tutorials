name         := "Molecule 3D"
organization := "scalafx.org"
version      := "0.2.5"

scalaVersion := "2.13.8"

resolvers += Resolver.sonatypeRepo("snapshots")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies += "org.scalafx" %% "scalafx" % "18.0.1-R27"

// Add OS specific JavaFX dependencies
libraryDependencies ++=
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
    .map(m => "org.openjfx" % s"javafx-$m" % "18.0.1")

resolvers += Opts.resolver.sonatypeSnapshots

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
