package co.technius.scalajs.mithril.tagsext.raw

import co.technius.scalajs.mithril.VNode
import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import org.scalajs.dom.raw.Node

@JSImport("mithril/render/vnode", JSImport.Namespace)
@js.native
class RawVNode(
    tag: String,
    key: js.UndefOr[String],
    attrs: js.UndefOr[js.Object],
    children: VNode.Child,
    text: js.UndefOr[String],
    dom: js.UndefOr[Node]) extends js.Object {
}

object RawVNode {
  implicit def toVNode(raw: RawVNode): VNode = raw.asInstanceOf[VNode]
}
