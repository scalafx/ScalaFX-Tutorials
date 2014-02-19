name := "ScalaFXML Example"

version := "1.0.0"

scalaVersion := "2.10.3"

resourceDirectory in Compile := (scalaSource in Compile).value

// Add managed dependency on ScalaFX library
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx"        % "1.0.0-M7",
  "org.scalafx" %% "scalafxml-core" % "0.1"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M1" cross CrossVersion.full)

// Add unmanaged dependency on JavaFX library based on JAVA_HOME variable
unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/jfxrt.jar"))

// Fork a new JVM for 'run' and 'test:run'
fork := true