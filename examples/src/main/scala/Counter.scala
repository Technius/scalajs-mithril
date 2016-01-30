import scala.scalajs.js
import scala.scalajs.js.Dynamic.{ literal => json }
import org.scalajs.dom

import co.technius.scalajs.mithril._

object Counter extends js.JSApp {
  def main(): Unit = {
    m.mount(dom.document.getElementById("app"), CounterComponent)
  }
}

object CounterComponent extends Component {

  override val controller: js.Function = () => new CounterCtrl

  @inline def btn(callback: js.Function, label: String) =
    m("button", json("onclick" -> callback), label)

  val view: js.Function = (ctrl: CounterCtrl) => js.Array[VirtualDom](
    m("p", js.Array(
      m("span", "Count: "),
      ctrl.count()
    )),
    btn(ctrl.increment, "Increment"),
    btn(ctrl.decrement, "Decrement"),
    btn(ctrl.reset, "Reset")
  )

  private[this] class CounterCtrl {
    val count: MithrilProp[Int] = m.prop(0)

    val increment = () => count() = _ + 1
    val decrement = () => count() = math.max(0, count() - 1)
    val reset = () => count() = 0
  }
}
