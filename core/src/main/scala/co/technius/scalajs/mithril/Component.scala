package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation._
import scala.annotation.meta.field

@ScalaJSDefined
trait Component[State, Attrs] extends Lifecycle[State, Attrs] {
  def view: js.Function1[RootNode, VNode]
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

  def oninit: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  def oncreate: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  def onupdate: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  def onbeforeremove: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  def onremove: js.UndefOr[js.Function1[RootNode, Unit]] = js.undefined

  def onbeforeupdate: js.UndefOr[js.Function2[RootNode, RootNode, Unit]] = js.undefined
}

object Component {
  /**
    * Constructs a component given the state and a view function `State =>
    * VNode`. Due to the way Mithril works, this function is implemented by
    * wrapping the State in another object.
    * @tparam S The state
    * @tparam A The attributes
    * @param stateF The function to pass to `oninit` that will generate the state.
    * @param viewF The view function.
    */
  def stateful[S, A](stateF: GenericVNode[S, A] => S)(viewF: GenericVNode[S, A] => VNode): Component[S, A] =
    new Component[S, A] {
      override val oninit: js.UndefOr[js.Function1[GenericVNode[S,A], Unit]] =
        js.defined { vnode: GenericVNode[S,A] =>
          vnode.state = stateF(vnode)
        }

      override val view: js.Function1[GenericVNode[S,A], VNode] = viewF
    }

  /**
    * Constructs a component given a function that instantiates the view. Best
    * used for stateless components.
    * @tparam Attrs The attributes passed to the root node
    */
  def viewOnly[Attrs](viewF: GenericVNode[js.Object, Attrs] => VNode): Component[js.Object, Attrs] =
    new Component[js.Object, Attrs] {
      override val view: js.Function1[GenericVNode[js.Object,Attrs], VNode] = viewF
    }
}
