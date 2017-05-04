package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation._
import scala.annotation.meta.field

@ScalaJSDefined
trait Component[State, Attrs] extends Lifecycle[State, Attrs] {
  def view(vnode: RootNode): VNode
}

/**
  * A facade for mithril's lifecycle methods, each defined as a `js.Function`.
  * @example {{{
  * val attrs = new Lifecycle[js.Object, js.Object] {
  *   override def oninit = js.defined { (vnode: RootNode) =>
  *     println(vnode.attrs)
  *   }
  * }
  * }}}
  */
@ScalaJSDefined
trait Lifecycle[State, Attrs] extends js.Object {

  /**
    * A convenient alias for the VNode type.
    */
  type RootNode = GenericVNode[State, Attrs]

  val oninit: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  val oncreate: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  val onupdate: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  val onbeforeremove: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  val onremove: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  val onbeforeupdate: js.UndefOr[js.Function2[RootNode, RootNode, Unit]] = js.undefined
}
