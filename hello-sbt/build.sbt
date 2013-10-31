// Name of the project
name := "Hello SBT"

// Project version
version := "1.0.1"

// Version of Scala used by the project
scalaVersion := "2.10.3"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "1.0.0-M6"

// Add dependency on JavaFX library based on JAVA_HOME variable
unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/jfxrt.jar"))
