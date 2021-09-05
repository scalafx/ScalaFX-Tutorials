package splash_demo

import javafx.{collections => jfxc, concurrent => jfxr}
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.beans.property.ReadOnlyObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.ListView
import scalafx.scene.image.Image
import scalafx.stage.{Stage, StageStyle}

import scala.language.postfixOps

/**
 * Splash screen demo based JavaFX version from [[https://gist.github.com/jewelsea/2305098]]
 */
object SplashDemoApp extends JFXApp3 {

  private val ApplicationIcon = "http://cdn1.iconfinder.com/data/icons/Copenhagen/PNG/32/people.png"

  private val friendTask = new jfxr.Task[jfxc.ObservableList[String]]() {

    protected def call: jfxc.ObservableList[String] = {
      val foundFriends     = new ObservableBuffer[String]()
      val availableFriends = ObservableBuffer[String](
        "Fili",
        "Kili",
        "Oin",
        "Gloin",
        "Thorin",
        "Dwalin",
        "Balin",
        "Bifur",
        "Bofur",
        "Bombur",
        "Dori",
        "Nori",
        "Ori"
        )

      updateMessage("Finding friends . . .")

      for (i <- availableFriends.indices) {
        Thread.sleep(400)
        updateProgress(i + 1, availableFriends.size)
        val nextFriend = availableFriends.get(i)
        foundFriends += nextFriend
        updateMessage("Finding friends . . . found " + nextFriend)
      }
      Thread.sleep(400)
      updateMessage("All friends found.")
      foundFriends
    }
  }

  override def start(): Unit = {
    // Show splash stage
    Splash.show(new JFXApp3.PrimaryStage(), friendTask, () => showMainStage(friendTask.value))

    // Start background task, splash stage will fade when the task is done
    new Thread(friendTask).start()
  }

  private def showMainStage(friends: ReadOnlyObjectProperty[jfxc.ObservableList[String]]): Unit = {
    val mainStage = new Stage(StageStyle.Decorated) {
      title = "My Friends"
      icons += new Image(ApplicationIcon)
    }

    val peopleView = new ListView[String] {
      items <== friends
    }
    mainStage.scene = new Scene(peopleView)
    mainStage.show()
  }

}
