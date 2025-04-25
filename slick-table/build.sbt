name         := "Slick Table"
organization := "org.scalafx"
version      := "0.3"

scalaVersion := "2.13.16"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "org.scalafx"        %% "scalafx"   % "24.0.0-R35",
  "com.typesafe.slick" %% "slick"     % "3.6.0",
  "org.slf4j"           % "slf4j-nop" % "2.0.17",
  "com.h2database"      % "h2"        % "2.3.232"
  )

resolvers ++= Opts.resolver.sonatypeOssSnapshots

// Fork a new JVM for 'run' and 'test:run' to avoid JavaFX double initialization problems
fork := true
