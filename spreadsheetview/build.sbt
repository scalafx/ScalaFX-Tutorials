name := "SpreadsheetView"
scalaVersion := "2.13.8"

libraryDependencies += "org.scalafx" %% "scalafx" % "18.0.1-R28"
libraryDependencies += "org.controlsfx" % "controlsfx" % "11.1.1"

resolvers += Resolver.mavenLocal

// Prevent startup bug in JavaFX
fork := true

scalacOptions += "-feature"


resolvers ++= Opts.resolver.sonatypeOssSnapshots
