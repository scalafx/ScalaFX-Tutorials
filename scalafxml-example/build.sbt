name := "ScalaFXML Example"

version := "1.0.2"

scalaVersion := "2.11.8"

resourceDirectory in Compile := (scalaSource in Compile).value

// Add managed dependency on ScalaFX library
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.72-R10-SNAPSHOT",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.2.2"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

// Fork a new JVM for 'run' and 'test:run'
fork := true