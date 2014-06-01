name := "ScalaFXML Example"

version := "1.0.1"

scalaVersion := "2.10.4"

resourceDirectory in Compile := (scalaSource in Compile).value

// Add managed dependency on ScalaFX library
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx"        % "1.0.0-R8",
  "org.scalafx" %% "scalafxml-core" % "0.2"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0" cross CrossVersion.full)

// Add dependency on JavaFX library (only for Java 7)
unmanagedJars in Compile += Attributed.blank(file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar")

// Fork a new JVM for 'run' and 'test:run'
fork := true