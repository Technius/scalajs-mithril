package co.technius.scalajs

import scala.language.implicitConversions
import scala.scalajs.js

package object mithril {
  val m = Mithril

  implicit def scalaToMithrilComponent(comp: ViewComponent): MithrilComponent = comp match {
    case c: Component =>
      val ctrl: js.Function = () => c.controller()
      val view: js.Function = (ctrl: c.Controller) => c.view(ctrl)
      js.Dynamic.literal(controller = ctrl, view = view).asInstanceOf[MithrilComponent]
    case c: ViewComponent =>
      val view: js.Function = () => c.view()
      js.Dynamic.literal(view = view).asInstanceOf[MithrilComponent]
  }
}
