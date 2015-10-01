package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation._

@JSExportDescendentObjects
@ScalaJSDefined
trait ViewComponent extends js.Object {
  def view(): VirtualDom | js.Array[VirtualDom]
}

@JSExportDescendentObjects
@ScalaJSDefined
trait Component extends js.Object {
  type Controller
  def controller(): Controller
  def view(ctrl: Controller): VirtualDom | js.Array[VirtualDom]
}

