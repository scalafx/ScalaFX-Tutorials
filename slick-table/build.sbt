// @formatter:off

name         := "Slick Table"
organization := "org.scalafx"
version      := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalafx"        %% "scalafx"   % "8.0.102-R11",
  "com.typesafe.slick" %% "slick"     % "3.1.1",
  "org.slf4j"           % "slf4j-nop" % "1.6.4",
  "com.h2database"      % "h2"        % "1.3.175"
)

shellPrompt := { state => System.getProperty("user.name") + "> " }

// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
//mainClass in (Compile, run) := Some("org.scalafx.ScalaFXHelloWorld")

// Fork a new JVM for 'run' and 'test:run' to avoid JavaFX double initialization problems
fork := true