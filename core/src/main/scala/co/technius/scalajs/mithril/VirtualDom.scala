package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.|

@js.native
trait VirtualDom extends js.Object {
  val tag: String = js.native
  val attrs: js.Object = js.native
  val children: js.Array[VirtualDom.Child] = js.native
}

object VirtualDom {
  type Child = String | js.Array[VirtualDom] | VirtualDom

  def apply(tag: String, attrs: js.Object, children: js.Array[Child]): VirtualDom =
    js.Dynamic.literal(
      "tag" -> tag,
      "attrs" -> attrs,
      "children" -> children).asInstanceOf[VirtualDom]
}
