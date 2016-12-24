package splash_demo

import scalafx.Includes._
import scalafx.animation.FadeTransition
import scalafx.concurrent.{Task, Worker}
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Label, ProgressBar}
import scalafx.scene.effect.DropShadow
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.VBox
import scalafx.stage.{Screen, Stage, StageStyle}

/**
  */
object Splash {

  private val SplashImage = "http://fxexperience.com/wp-content/uploads/2010/06/logo.png"
  private val SplashWidth = 676
  private val SplashHeight = 227

  private val splash = new ImageView(new Image(SplashImage))
  private val loadProgress = new ProgressBar {
    prefWidth = SplashWidth - 20
  }
  private val progressText = new Label("Will find friends for peanuts . . .") {
    alignment = Pos.Center
  }
  private val splashLayout = new VBox() {
    children ++= Seq(splash, loadProgress, progressText)
    style =
      "-fx-padding: 5; " +
        "-fx-background-color: cornsilk; " +
        "-fx-border-width:5; " +
        "-fx-border-color: " +
        "linear-gradient(" +
        "to bottom, " +
        "chocolate, " +
        "derive(chocolate, 50%)" +
        ");"
    effect = new DropShadow()
  }

  def show(splashStage: Stage, loaderTask: Task[_], onSuccess: () => Unit): Unit = {
    progressText.text <== loaderTask.message
    loadProgress.progress <== loaderTask.progress
    loaderTask.state.onChange { (_, _, newState) =>
      newState match {
        case Worker.State.Succeeded.delegate =>
          loadProgress.progress.unbind()
          loadProgress.progress = 1
          new FadeTransition(0.5.s, splashLayout) {
            fromValue = 1.0
            toValue = 0.0
            onFinished = handle {
              splashStage.hide()
            }
          }.play()

          onSuccess()

        case _ =>
        // TODO: handle other states
      }
    }


    splashStage.initStyle(StageStyle.Undecorated)
    splashStage.alwaysOnTop = true
    splashStage.scene = new Scene(splashLayout)
    val bounds = Screen.primary.bounds
    splashStage.x = bounds.minX + bounds.width / 2 - SplashWidth / 2
    splashStage.y = bounds.minY + bounds.height / 2 - SplashHeight / 2
  }


}
