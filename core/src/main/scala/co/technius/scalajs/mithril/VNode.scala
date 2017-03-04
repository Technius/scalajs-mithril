package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.|

@js.native
sealed trait GenericVNode[State] extends js.Object {
  type TagType
  val tag: TagType = js.native
  val key: js.UndefOr[String] = js.native
  val attrs: js.Dictionary[Any] = js.native
  val children: js.Array[VNode] = js.native
  val text: js.UndefOr[String] = js.native
  var state: State = js.native
}

@js.native
sealed trait VNode extends GenericVNode[js.Object]

object VNode {

  @js.native
  trait Element extends VNode {
    type TagType = String
  }

  object Element {
    def unapply(vnode: VNode): Option[Element] =
      if (vnode.tag.isInstanceOf[String] && vnode.tag != Fragment.tag &&
          vnode.tag != Trusted.tag && vnode.tag != Text.tag)
        Some(vnode.asInstanceOf[Element])
      else
        None
  }

  @js.native
  trait Fragment extends VNode {
    type TagType = String
    val domSize: Int = js.native
    override val tag = Fragment.tag
  }

  object Fragment {
    val tag = "["
    def unapply(vnode: VNode): Option[Fragment] =
      if (vnode.tag == tag) Some(vnode.asInstanceOf[Fragment]) else None
  }

  @js.native
  trait Text extends VNode {
    type TagType = String
    override val tag = Text.tag
  }

  object Text {
    val tag = "#"
    def unapply(vnode: VNode): Option[Text] =
      if (vnode.tag == tag) Some(vnode.asInstanceOf[Text]) else None
  }

  @js.native
  trait Trusted extends VNode {
    type TagType = String
    val domSize: Int = js.native
    override val tag = Trusted.tag
  }

  object Trusted {
    val tag = "<"
    def unapply(vnode: VNode): Option[Trusted] =
      if (vnode.tag == tag) Some(vnode.asInstanceOf[Trusted]) else None
  }

  private[this] type MComponent = co.technius.scalajs.mithril.Component

  @js.native
  trait Component extends VNode {
    type TagType = MComponent
  }

  type Child = VNode | js.Array[VNode] | String | Double
}
