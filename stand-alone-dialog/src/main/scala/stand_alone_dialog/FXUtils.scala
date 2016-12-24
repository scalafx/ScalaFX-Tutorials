package stand_alone_dialog

import java.util.concurrent

import scalafx.application.Platform

object FXUtils {

  /**
   * Run operation `op` on FX application thread and wait for completion.
   * If the current thread is the FX application, the operation will be run on it.
   *
   * @param op operation to be performed.
   */
  def onFXAndWait[R](op: => R): R = {
    if (Platform.isFxApplicationThread) {
      op
    } else {
      val callable = new concurrent.Callable[R] {
        override def call(): R = op
      }
      val future = new concurrent.FutureTask(callable)
      Platform.runLater(future)
      future.get()
    }
  }
}
