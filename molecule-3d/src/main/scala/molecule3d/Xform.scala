package molecule3d

import scalafx.Includes._
import scalafx.scene.transform.{Rotate, Scale, Translate}

object Xform {
  object RotateOrder {
    final val XYZ = RotateOrder("XYZ")
    final val XZY = RotateOrder("XZY")
    final val YXZ = RotateOrder("YXZ")
    final val YZX = RotateOrder("YZX")
    final val ZXY = RotateOrder("ZXY")
    final val ZYX = RotateOrder("ZYX")
  }

  sealed case class RotateOrder(name: String)

}

class Xform extends javafx.scene.Group {
  private val _t = new Translate()
  private val p = new Translate()
  private val ip = new Translate()
  private val _rx = new Rotate {
    axis = Rotate.XAxis
  }
  private val _ry = new Rotate {
    axis = Rotate.YAxis
  }
  private val _rz = new Rotate {
    axis = Rotate.ZAxis
  }
  private val s = new Scale

  transforms ++= Seq(t, _rz, _ry, _rx, s)

  def this(rotateOrder: Xform.RotateOrder) = {
    this()
    import Xform.RotateOrder._
    rotateOrder match {
      case XYZ => transforms ++= Seq(t, p, _rz, _ry, _rx, s, ip)
      case XZY => transforms ++= Seq(t, p, _ry, _rz, _rx, s, ip)
      case YXZ => transforms ++= Seq(t, p, _rz, _rx, _ry, s, ip)
      case YZX => transforms ++= Seq(t, p, _rx, _rz, _ry, s, ip)
      case ZXY => transforms ++= Seq(t, p, _ry, _rx, _rz, s, ip)
      case ZYX => transforms ++= Seq(t, p, _rx, _ry, _rz, s, ip)
    }
  }

  def children = getChildren
  
  def transforms = getTransforms

  def setTranslate(x: Double, y: Double, z: Double): Unit = {
    t.x = x
    t.y = y
    t.z = z
  }

  def setTranslate(x: Double, y: Double): Unit = {
    t.x = x
    t.y = y
  }

  def rx : Rotate = _rx
  def ry : Rotate = _ry
  def rz : Rotate = _rz
  def t : Translate = _t

  def setRotate(x: Double, y: Double, z: Double): Unit = {
    _rx.angle = x
    _ry.angle = y
    _rz.angle = z
  }

  def setRotateX(x: Double): Unit = {
    _rx.angle = x
  }

  def rotateY = _ry.angle

  def rotateY_=(y: Double): Unit = {
    _ry.angle = y
  }

  def rotateZ:Double = _rz.angle()

  def rotateZ_=(z: Double): Unit = {
    _rz.angle = z
  }

  def setRx(x: Double): Unit = {
    _rx.angle = x
  }

  def setRy(y: Double): Unit = {
    _ry.angle = y
  }

  def setRz(z: Double): Unit = {
    _rz.angle = z
  }

  def setScale(scaleFactor: Double): Unit = {
    s.x = scaleFactor
    s.y = scaleFactor
    s.z = scaleFactor
  }

  def setScale(x: Double, y: Double, z: Double): Unit = {
    s.x=x
    s.y=y
    s.z = z
  }

  def setSx(x: Double): Unit = {
    s.x = x
  }

  def setSy(y: Double): Unit = {
    s.y = y
  }

  def setSz(z: Double): Unit = {
    s.z = z
  }

  def setPivot(x: Double, y: Double, z: Double): Unit = {
    p.x = x
    p.y = y
    p.z = z
    ip.x = -x
    ip.y = -y
    ip.z = -z
  }

  def reset(): Unit = {
    t.x = 0.0
    t.y = 0.0
    t.z = 0.0
    _rx.angle = 0.0
    _ry.angle = 0.0
    _rz.angle = 0.0
    s.x = 1.0
    s.y = 1.0
    s.z = 1.0
    p.x = 0.0
    p.y = 0.0
    p.z = 0.0
    ip.x = 0.0
    ip.y = 0.0
    ip.z = 0.0
  }

  def resetTSP(): Unit = {
    t.x = 0.0
    t.y = 0.0
    t.z = 0.0
    s.x = 1.0
    s.y = 1.0
    s.z = 1.0
    p.x = 0.0
    p.y = 0.0
    p.x = 0.0
    ip.x = 0.0
    ip.y = 0.0
    ip.z = 0.0
  }
}
