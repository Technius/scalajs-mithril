import scala.scalajs.js
import scala.scalajs.js.Dynamic.{ literal => json }
import scala.scalajs.js.annotation.ScalaJSDefined
import org.scalajs.dom

import co.technius.scalajs.mithril._

@ScalaJSDefined
object CounterComponent extends Component {

  type RootNode = GenericVNode[CounterState, _]

  def oninit(vnode: RootNode) = {
    vnode.state = new CounterState
  }

  def view(vnode: RootNode) = {
    import vnode.state
    import helpers._
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

  protected class CounterState {
    val count: MStream[Int] = MithrilStream(0)

    val increment = () => count() = _ + 1
    val decrement = () => count() = math.max(0, count() - 1)
    val reset = () => count() = 0
  }

  object helpers {
    def btn(callback: js.Function, label: String) =
      m("button", json("onclick" -> callback), label)
  }
}
