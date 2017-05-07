import co.technius.scalajs.mithril._
import scala.scalajs.js
import org.scalajs.dom

object TreeDemo {

  // Since this component is recursive, it requires explicit type annotations
  val component: Component[State, Attrs] = Component.stateful[State, Attrs](_ => new State) { vnode =>
    import vnode.state
    val children = state.children.map { c =>
      m("li", m(component, Attrs(c)))
    }

    val addChildBtn = m("button", js.Dynamic.literal("onclick" -> addChild(state) _), "+")

    m("div", js.Array[VNode](
      m("span", vnode.attrs.name),
      addChildBtn,
      m("ul", js.Array[VNode](
        m.fragment(new js.Object, children)
      ))
    ))
  }

  def addChild(state: State)(clickEvent: dom.raw.Event): Unit = {
    val name = dom.window.prompt("Enter child name").trim
    if (!name.isEmpty) {
      state.children += name
    }
  }

  class State {
    val children: js.Array[String] = js.Array()
  }

  case class Attrs(name: String)
}
