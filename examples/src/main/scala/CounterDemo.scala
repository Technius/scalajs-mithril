import scala.scalajs.js
import org.scalajs.dom

import co.technius.scalajs.mithril._

object CounterDemo {

  val component = Component.stateful[State, js.Object](_ => new State) { vnode =>
    import vnode.state
    m("div", js.Array[VNode](
      m("p", js.Array(
        m("span", "Count: "),
        state.count()
      )),
      btn(state.increment, "Increment"),
      btn(state.decrement, "Decrement"),
      btn(state.reset, "Reset")
    ))
  }

  def btn(callback: js.Function, label: String) =
    m("button", js.Dynamic.literal("onclick" -> callback), label)

  protected class State {
    val count: MStream[Int] = MithrilStream(0)

    val increment = () => count.update(_ + 1)
    val decrement = () => count.update(c => math.max(0, c - 1))
    val reset = () => count() = 0
  }
}
