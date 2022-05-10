ScalaFX-Tutorials
=================

[![Scala CI](https://github.com/scalafx/ScalaFX-Tutorials/actions/workflows/scala.yml/badge.svg)](https://github.com/scalafx/ScalaFX-Tutorials/actions/workflows/scala.yml)

Examples of using ScalaFX. Each example is a stand-alone complete project

* [__hello-sbt__](hello-sbt):
  a basic example of using [Simple-Build-Tool](http://www.scala-sbt.org/) (SBT) and
  [ScalaFX](http://scalafx.org). Detailed description can be found in the blog post
  ["Getting Started with ScalaFX: Compile and Run"](http://codingonthestaircase.wordpress.com/2013/05/17/getting-started-with-scalafx-compile-and-run-2/)
  .

* [__cell_factories__](cell_factories) - examples of using cell factories, including custom cell factories.

* [__event-filters__](event-filters):
  demonstrates use of ScalaFX Event Filter API. The code is based on JavaFX example
  [Handling JavaFX Events, Part 3 Working with Event Filters](http://docs.oracle.com/javafx/2/events/filters.htm).

* [__molecule-3d__](molecule-3d):
  ScalaFX 8 example of using 3D graphics. Based on tutorial
  [Getting Started with JavaFX 3D Graphics](http://docs.oracle.com/javafx/8/3d_graphics/jfxpub-3d_graphics.htm)
  by Cindy Castillo and John Yoon.

* [__sam_event_handlers__](sam_event_handlers):
  Scala 2.11 provides for Java8-like support for using lambdas in places of interfaces with a Single Abstract Method (
  SAM). The example shows required compiler options and use with ScalaFX.

* [__scalafxml-example__](scalafxml-example):
  demonstrates use of FXML with ScalaFX using [ScalaFXML](https://github.com/vigoo/scalafxml)
  library.

* [__Slick-table__](slick-table): An example of using a table view with database interfaced
  through [Slick](http://slick.lightbend.com/) API.

* [__splash-demo__](splash-demo): Displays a splash stage with a progress bar during startup then opens main application
  stage.

* [__SpreadsheetView__](spreadsheetview): Examples of
  using [ContolsFX SpreadsheetView](https://github.com/controlsfx/controlsfx/wiki/ControlsFX-Features#spreadsheetview)
  from ScalaFX.

* [__stand-alone-dialog__](stand-alone-dialog):
  shows how to display a ScalaFX dialog (stage) without using `JFXApp3`. You can use this approach, for instance, to
  show a JavaFX dialog from a command line or from a Swing application.
