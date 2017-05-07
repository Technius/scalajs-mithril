import co.technius.scalajs.mithril._
import scala.scalajs.js

object HelloDemo {

  val component = Component.stateful[State, js.Object](_ => new State) { vnode =>
    import vnode.state
    m("div", js.Array[VNode](
      m("span", s"Hi, ${state.name()}!"),
      m("input[type=text]", js.Dynamic.literal(
        oninput = m.withAttr("value", state.name),
        value = state.name()
      ))
    ))
  }

  protected class State {
    val name = MithrilStream("Name")
  }
}
