name         := "SpreadsheetView"
scalaVersion := "2.13.16"

libraryDependencies += "org.scalafx"   %% "scalafx"    % "24.0.0-R35"
libraryDependencies += "org.controlsfx" % "controlsfx" % "11.2.2"

resolvers += Resolver.mavenLocal

// Prevent startup bug in JavaFX
fork := true

scalacOptions += "-feature"

resolvers ++= Opts.resolver.sonatypeOssSnapshots
