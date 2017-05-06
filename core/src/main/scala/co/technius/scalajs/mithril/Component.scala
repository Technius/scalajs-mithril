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

object Component {
  /**
    * Constructs a component given the state and a view function `State =>
    * VNode`. Due to the way Mithril works, this function is implemented by
    * wrapping the State in another object.
    * @tparam State The state
    */
  // TODO: Don't use ugly hacks
  def build[State](state: State)(viewF: State => VNode): Component[js.Object, js.Object] =
    new Component[js.Object, js.Object] {
      // when oninit is defined, view and other variables aren't captured for some reason
      override val oninit = js.defined { vnode =>
        vnode.state = new WrappedState(state, viewF)
      }

      override def view(vnode: RootNode) = {
        val wrappedState = vnode.state.asInstanceOf[WrappedState[State]]
        wrappedState.view(wrappedState.state)
      }
    }

  @ScalaJSDefined
  private class WrappedState[State](val state: State, val view: State => VNode)
      extends js.Object

  /**
    * Constructs a component given a function that instantiates the view. Best
    * used for stateless components.
    * @tparam Attrs The attributes passed to the root node
    */
  def viewOnly[Attrs](viewF: GenericVNode[js.Object, Attrs] => VNode): Component[js.Object, Attrs] =
    new Component[js.Object, Attrs] {
      override def view(vnode: RootNode) = viewF(vnode)
    }
}
