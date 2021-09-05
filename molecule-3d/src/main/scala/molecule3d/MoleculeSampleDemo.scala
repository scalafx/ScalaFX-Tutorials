package molecule3d

import scalafx.Includes._
import scalafx.animation.Timeline
import scalafx.application.JFXApp3
import scalafx.scene._
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.paint.{Color, PhongMaterial}
import scalafx.scene.shape.{Box, Cylinder, Sphere}
import scalafx.scene.transform.Rotate

/**
 * ScalaFX implementation of `MoleculeSampleApp` from tutorial
 * [[http://docs.oracle.com/javafx/8/3d_graphics/jfxpub-3d_graphics.htm Getting Started with JavaFX 3D Graphics]]
 * by Cindy Castillo and John Yoon.
 *
 * @author Jarek Sacha
 */
object MoleculeSampleDemo extends JFXApp3 {
  app =>
  System.setProperty("prism.dirtyopts", "false")

  private final val root                                  = new Group()
  private final val axisGroup                             = new Group()
  private final val world                                 = new Xform()
  private final val camera            : PerspectiveCamera = new PerspectiveCamera(true)
  private final val cameraXform                           = new Xform()
  private final val cameraXform2                          = new Xform()
  private final val cameraXform3                          = new Xform()
  private final val cameraDistance    : Double            = 450
  private final val moleculeGroup                         = new Xform()
  private       val timeline          : Timeline          = null
  private var timelinePlaying                             = false
  private var ONE_FRAME               : Double            = 1.0 / 24.0
  private var DELTA_MULTIPLIER        : Double            = 200.0
  private       val CONTROL_MULTIPLIER: Double            = 0.1
  private       val SHIFT_MULTIPLIER  : Double            = 0.1
  private       val ALT_MULTIPLIER    : Double            = 0.5
  private var mousePosX               : Double            = .0
  private var mousePosY               : Double            = .0
  private var mouseOldX               : Double            = .0
  private var mouseOldY               : Double            = .0
  private var mouseDeltaX             : Double            = .0
  private var mouseDeltaY             : Double            = .0

  override def start(): Unit = {
    buildScene()
    buildCamera()
    buildAxes()
    buildMolecule()

    stage = new JFXApp3.PrimaryStage {
      scene = new Scene(root, 1024, 768, depthBuffer = true, antiAliasing = SceneAntialiasing.Balanced) {
        fill = Color.Gray
        title = "Molecule Sample Application"
        camera = app.camera
      }
      handleKeyboard(scene(), world)
      handleMouse(scene(), world)
    }
  }

  private def buildScene(): Unit = {
    root.children += world
  }

  private def buildCamera(): Unit = {
    root.children += cameraXform
    cameraXform.children += cameraXform2
    cameraXform2.children += cameraXform3
    cameraXform3.children += camera
    cameraXform3.rotateZ = 180.0
    camera.nearClip = 0.1
    camera.farClip = 10000.0
    camera.translateZ = -cameraDistance
    cameraXform.ry.angle = 320.0
    cameraXform.rx.angle = 40
  }

  private def buildAxes(): Unit = {
    val redMaterial = new PhongMaterial {
      diffuseColor = Color.DarkRed
      specularColor = Color.Red
    }
    val greenMaterial = new PhongMaterial {
      diffuseColor = Color.DarkGreen
      specularColor = Color.Green
    }
    val blueMaterial = new PhongMaterial {
      diffuseColor = Color.DarkBlue
      specularColor = Color.Blue
    }
    val xAxis = new Box(240.0, 1, 1) {
      material = redMaterial
    }
    val yAxis = new Box(1, 240.0, 1) {
      material = greenMaterial
    }
    val zAxis = new Box(1, 1, 240.0) {
      material = blueMaterial
    }
    axisGroup.children ++= Seq(xAxis, yAxis, zAxis)
    world.children += axisGroup
  }

  private def buildMolecule(): Unit = {
    val redMaterial = new PhongMaterial {
      diffuseColor = Color.DarkRed
      specularColor = Color.Red
    }
    val whiteMaterial = new PhongMaterial {
      diffuseColor = Color.White
      specularColor = Color.LightBlue
    }
    val greyMaterial = new PhongMaterial {
      diffuseColor = Color.DarkGrey
      specularColor = Color.Grey
    }
    val oxygenSphere = new Sphere(40.0) {
      material = redMaterial
    }
    val hydrogen1Sphere = new Sphere(30.0) {
      material = whiteMaterial
      translateX = 0.0
    }
    val hydrogen2Sphere = new Sphere(30.0) {
      material = whiteMaterial
      translateZ = 0.0
    }
    val bond1Cylinder = new Cylinder(5, 100) {
      material = greyMaterial
      translateX = 50.0
      rotationAxis = Rotate.ZAxis
      rotate = 90.0
    }
    val bond2Cylinder = new Cylinder(5, 100) {
      material = greyMaterial
      translateX = 50.0
      rotationAxis = Rotate.ZAxis
      rotate = 90.0
    }
    val moleculeXform = new Xform {
      children ++= Seq(
        // Oxygen
        oxygenSphere,
        // Hydrogen 1
        new Xform {
          children ++= Seq(
            new Xform {
              children += hydrogen1Sphere
              t.x = 100
            },
            bond1Cylinder
          )
        },
        // Hydrogen 2
        new Xform {
          rotateY = 104.0
          children ++= Seq(
            new Xform {
              children += hydrogen2Sphere
              t.x = 100
            },
            bond2Cylinder
          )
        }
      )
    }
    moleculeGroup.children += moleculeXform
    world.children += moleculeGroup
  }

  private def handleMouse(scene: Scene, root: Node): Unit = {
    scene.onMousePressed = (me) => {
      mousePosX = me.sceneX
      mousePosY = me.sceneY
      mouseOldX = me.sceneX
      mouseOldY = me.sceneY
    }
    scene.onMouseDragged = (me) => {
      mouseOldX = mousePosX
      mouseOldY = mousePosY
      mousePosX = me.sceneX
      mousePosY = me.sceneY
      mouseDeltaX = mousePosX - mouseOldX
      mouseDeltaY = mousePosY - mouseOldY
      val modifier       = if (me.isControlDown) 0.1 else if (me.isShiftDown) 10 else 1.0
      val modifierFactor = 0.1
      if (me.isPrimaryButtonDown) {
        cameraXform.ry.angle = cameraXform.ry.angle() - mouseDeltaX * modifierFactor * modifier * 2.0
        cameraXform.rx.angle = cameraXform.rx.angle() + mouseDeltaY * modifierFactor * modifier * 2.0
      } else if (me.isSecondaryButtonDown) {
        val z    = camera.translateZ()
        val newZ = z + mouseDeltaX * modifierFactor * modifier
        camera.translateZ = newZ
      } else if (me.isMiddleButtonDown) {
        cameraXform2.t.x = cameraXform2.t.x() + mouseDeltaX * modifierFactor * modifier * 0.3
        cameraXform2.t.x = cameraXform2.t.y() + mouseDeltaY * modifierFactor * modifier * 0.3
      }
    }
  }

  private def handleKeyboard(scene: Scene, root: Node): Unit = {
    //    val moveCamera: Boolean = true
    scene.onKeyPressed = (event: KeyEvent) => {
      //      val currentTime: Duration = null
      event.code match {
        case KeyCode.Z =>
          if (event.isShiftDown) {
            cameraXform.ry.setAngle(0.0)
            cameraXform.rx.setAngle(0.0)
            camera.setTranslateZ(-300.0)
          }
          cameraXform2.t.setX(0.0)
          cameraXform2.t.setY(0.0)
        case KeyCode.X =>
          if (event.isControlDown) {
            if (axisGroup.isVisible) {
              axisGroup.setVisible(false)
            } else {
              axisGroup.setVisible(true)
            }
          }
        case KeyCode.S =>
          if (event.isControlDown) {
            if (moleculeGroup.isVisible) {
              moleculeGroup.setVisible(false)
            } else {
              moleculeGroup.setVisible(true)
            }
          }
        case KeyCode.Space =>
          if (timelinePlaying) {
            timeline.pause()
            timelinePlaying = false
          } else {
            timeline.play()
            timelinePlaying = true
          }
        case KeyCode.Up =>
          if (event.isControlDown && event.isShiftDown) {
            cameraXform2.t.setY(cameraXform2.t.getY - 10.0 * CONTROL_MULTIPLIER)
          } else if (event.isAltDown && event.isShiftDown) {
            cameraXform.rx.setAngle(cameraXform.rx.getAngle - 10.0 * ALT_MULTIPLIER)
          } else if (event.isControlDown) {
            cameraXform2.t.setY(cameraXform2.t.getY - 1.0 * CONTROL_MULTIPLIER)
          } else if (event.isAltDown) {
            cameraXform.rx.setAngle(cameraXform.rx.getAngle - 2.0 * ALT_MULTIPLIER)
          } else if (event.isShiftDown) {
            val z   : Double = camera.getTranslateZ
            val newZ: Double = z + 5.0 * SHIFT_MULTIPLIER
            camera.setTranslateZ(newZ)
          }
        case KeyCode.Down =>
          if (event.isControlDown && event.isShiftDown) {
            cameraXform2.t.setY(cameraXform2.t.getY + 10.0 * CONTROL_MULTIPLIER)
          } else if (event.isAltDown && event.isShiftDown) {
            cameraXform.rx.setAngle(cameraXform.rx.getAngle + 10.0 * ALT_MULTIPLIER)
          } else if (event.isControlDown) {
            cameraXform2.t.setY(cameraXform2.t.getY + 1.0 * CONTROL_MULTIPLIER)
          } else if (event.isAltDown) {
            cameraXform.rx.setAngle(cameraXform.rx.getAngle + 2.0 * ALT_MULTIPLIER)
          } else if (event.isShiftDown) {
            val z   : Double = camera.getTranslateZ
            val newZ: Double = z - 5.0 * SHIFT_MULTIPLIER
            camera.setTranslateZ(newZ)
          }
        case KeyCode.Right =>
          if (event.isControlDown && event.isShiftDown) {
            cameraXform2.t.setX(cameraXform2.t.getX + 10.0 * CONTROL_MULTIPLIER)
          } else if (event.isAltDown && event.isShiftDown) {
            cameraXform.ry.setAngle(cameraXform.ry.getAngle - 10.0 * ALT_MULTIPLIER)
          } else if (event.isControlDown) {
            cameraXform2.t.setX(cameraXform2.t.getX + 1.0 * CONTROL_MULTIPLIER)
          } else if (event.isAltDown) {
            cameraXform.ry.setAngle(cameraXform.ry.getAngle - 2.0 * ALT_MULTIPLIER)
          }
        case KeyCode.Left =>
          if (event.isControlDown && event.isShiftDown) {
            cameraXform2.t.setX(cameraXform2.t.getX - 10.0 * CONTROL_MULTIPLIER)
          } else if (event.isAltDown && event.isShiftDown) {
            cameraXform.ry.setAngle(cameraXform.ry.getAngle + 10.0 * ALT_MULTIPLIER)
          } else if (event.isControlDown) {
            cameraXform2.t.setX(cameraXform2.t.getX - 1.0 * CONTROL_MULTIPLIER)
          } else if (event.isAltDown) {
            cameraXform.ry.setAngle(cameraXform.ry.getAngle + 2.0 * ALT_MULTIPLIER)
          }
        case _ =>
      }
    }
  }
}
