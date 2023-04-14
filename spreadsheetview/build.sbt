name         := "SpreadsheetView"
scalaVersion := "2.13.10"

libraryDependencies += "org.scalafx"   %% "scalafx"    % "20.0.0-R31"
libraryDependencies += "org.controlsfx" % "controlsfx" % "11.1.2"

resolvers += Resolver.mavenLocal

// Prevent startup bug in JavaFX
fork := true

scalacOptions += "-feature"

resolvers ++= Opts.resolver.sonatypeOssSnapshots
