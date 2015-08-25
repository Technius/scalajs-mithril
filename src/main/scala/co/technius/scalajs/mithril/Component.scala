package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.annotation._

@JSExportDescendentObjects
trait Component {
  type Controller
  @JSExport def controller(): Controller
  @JSExport def view(ctrl: Controller): js.Object
}
