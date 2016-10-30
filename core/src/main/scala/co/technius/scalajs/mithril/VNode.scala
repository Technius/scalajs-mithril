package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.|

@js.native
sealed trait GenericVNode[State, Attrs] extends js.Object {
  type TagType
  val tag: TagType = js.native
  val key: js.UndefOr[String] = js.native
  val attrs: js.UndefOr[Attrs] = js.native
  val children: js.Array[VNode] = js.native
  val text: js.UndefOr[String] = js.native
  var state: State = js.native
}

@js.native
sealed trait VNode extends GenericVNode[js.Object, js.Object]

object VNode {

  @js.native
  trait ElementNode extends VNode {
    type TagType = String
  }
  
  @js.native
  trait FragmentNode extends VNode {
    type TagType = String
    val domSize: Int = js.native
  }

  @js.native
  trait TextNode extends VNode {
    type TagType = String
  }

  @js.native
  trait TrustedNode extends VNode {
    type TagType = String
    val domSize: Int = js.native
  }
  
  @js.native
  trait ComponentNode extends VNode {
    type TagType = Component
  }
  
  type Child = js.Array[VNode] | String | Double
}
