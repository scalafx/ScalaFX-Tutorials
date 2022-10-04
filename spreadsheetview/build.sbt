name := "SpreadsheetView"
scalaVersion := "2.13.9"

libraryDependencies += "org.scalafx" %% "scalafx" % "19.0.0-R30"
libraryDependencies += "org.controlsfx" % "controlsfx" % "11.1.1"

resolvers += Resolver.mavenLocal

// Prevent startup bug in JavaFX
fork := true

scalacOptions += "-feature"


resolvers ++= Opts.resolver.sonatypeOssSnapshots
