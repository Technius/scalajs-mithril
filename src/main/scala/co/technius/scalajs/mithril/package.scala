package co.technius.scalajs

import scala.language.implicitConversions
import scala.scalajs.js

package object mithril {
  val m = Mithril

  implicit def scalaToMithrilComponent(c: Component): MithrilComponent = {
    val ctrl: js.Function = () => c.controller()
    val view: js.Function = (ctrl: c.Controller) => c.view(ctrl)
    js.Dynamic.literal(controller = ctrl, view = view).asInstanceOf[MithrilComponent]
  }
}
