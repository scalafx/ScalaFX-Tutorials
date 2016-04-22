SAM Event Handlers
==================

Syntax for event handlers in ScalaFX can be simplifies using new SAM (Single Abstract Method) feature of the Scala 2.11 compiler.
This corresponds to Java 8 syntax for using lambdas instead implementing interfaces with simple abstract method, like `Runnable`. 
For instance rather than creating anonymous class implementing `Runnable`, in Java 8, we can pass a lambda:

```java
new Thread(() -> println("hi")).run()
``` 

In Scala 2.11 you can do it too when you enable experimental features of the compiler:

```scala
scalacOptions += "-Xexperimental"
```

Here is a complete example using SAM for an event handler, exactly the way you were asking for: 

```scala
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox

object SAMDemo extends JFXApp {
  stage = new PrimaryStage {
    title = "SAM Demo"
    scene = new Scene {
      root = new HBox {
        children = Seq(
          new Button {
            text = "Print message (2)"
            onAction = { ae => println("some message") }
          }
        )
      }
    }
  }
}
```

Note the imports used. There is nothing related to event handling. 
You can import it but it is not necessary as the handler is created by Scala compiler using SAM feature.