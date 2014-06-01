// Name of the project
name := "Hello SBT"

// Project version
version := "1.0.3"

// Version of Scala used by the project
scalaVersion := "2.10.4"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "1.0.0-R8"

// Add dependency on JavaFX library (only for Java 7)
unmanagedJars in Compile += Attributed.blank(file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar")

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true
