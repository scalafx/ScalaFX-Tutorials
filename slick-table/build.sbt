name         := "Slick Table"
organization := "org.scalafx"
version      := "0.3"

scalaVersion := "2.13.12"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "org.scalafx"        %% "scalafx"   % "21.0.0-R32",
  "com.typesafe.slick" %% "slick"     % "3.4.1",
  "org.slf4j"           % "slf4j-nop" % "2.0.7",
  "com.h2database"      % "h2"        % "2.1.214"
  )

resolvers ++= Opts.resolver.sonatypeOssSnapshots

// Fork a new JVM for 'run' and 'test:run' to avoid JavaFX double initialization problems
fork := true
