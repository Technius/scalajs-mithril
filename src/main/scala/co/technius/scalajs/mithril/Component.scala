package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.annotation._

@JSExportDescendentObjects
trait ViewComponent {
  @JSExport def view(): js.Object
}

trait Component extends ViewComponent {
  type Controller
  @JSExport def controller(): Controller
  @JSExport def view(ctrl: Controller): js.Object

  override def view(): js.Object = throw new IllegalArgumentException()
}

