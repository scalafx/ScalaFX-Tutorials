package stand_alone_dialog

import java.util.concurrent.ExecutionException
import java.util.concurrent.locks.ReentrantLock
import scalafx.application.Platform

object FXUtils {


  /** Invokes a Runnable on JavaFX Application Thread and waits while it's finished. Like
    * SwingUtilities.invokeAndWait does for EDT.
    *
    * Based on blog post by Hendrik: "invokeAndWait for JavaFX"
    * http://www.guigarage.com/2013/01/invokeandwait-for-javafx/
    *
    * @param runnable The Runnable that has to be called on JFX thread.
    * @throws InterruptedException if the execution is interrupted.
    * @throws ExecutionException if a exception is occurred in the run method of the Runnable
    */
  def runAndWait(runnable: Runnable) {
    if (Platform.isFxApplicationThread) {
      try {
        runnable.run()
      } catch {
        case e: Exception => throw new ExecutionException(e)
      }
    } else {
      class ThrowableWrapper(var t: Throwable = null)

      val lock = new ReentrantLock()
      val condition = lock.newCondition()
      val throwableWrapper = new ThrowableWrapper()
      lock.lock()
      try {
        Platform.runLater(new Runnable() {

          override def run() {
            lock.lock()
            try {
              runnable.run()
            } catch {
              case e: Throwable => throwableWrapper.t = e
            } finally {
              try {
                condition.signal()
              } finally {
                lock.unlock()
              }
            }
          }
        })
        condition.await()
        if (throwableWrapper.t != null) {
          throw new ExecutionException(throwableWrapper.t)
        }
      } finally {
        lock.unlock()
      }
    }
  }

  /** Invokes spcified code blocl `op` on JavaFX Application Thread and waits while it's finished. Like
    * SwingUtilities.invokeAndWait does for EDT.
    *
    * Based on blog post by Hendrik: "invokeAndWait for JavaFX"
    * http://www.guigarage.com/2013/01/invokeandwait-for-javafx/
    *
    * @param op code block to be executed on JavaFX Application Thread.
    * @throws InterruptedException if the execution is interrupted.
    * @throws ExecutionException if a exception is occurred in the run method of the Runnable
    */
  def runAndWait(op: => Unit) {
    runAndWait(new Runnable {
      def run() {
        op
      }
    })
  }
}
