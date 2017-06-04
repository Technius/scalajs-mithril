package co.technius.scalajs.mithril.tagsext.raw

import co.technius.scalajs.mithril.VNode
import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import org.scalajs.dom.raw.Node

@JSImport("mithril/render/vnode", JSImport.Namespace)
@js.native
class RawVNode(
    val tag: String,
    val key: js.UndefOr[String],
    val attrs: js.UndefOr[js.Object],
    val children: VNode.Child,
    val text: js.UndefOr[String],
    val dom: js.UndefOr[Node]) extends js.Object {
}

object RawVNode {
  implicit def toVNode(raw: RawVNode): VNode = raw.asInstanceOf[VNode]
}
