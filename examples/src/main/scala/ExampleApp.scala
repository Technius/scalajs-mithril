import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import org.scalajs.dom

import co.technius.scalajs.mithril._

object ExampleApp extends js.JSApp {
  def main(): Unit = {
    m.mount(dom.document.getElementById("app"), Showcase.component)
  }
}

object Showcase {

  val defaultComponent = Component.viewOnly[js.Object] { _ =>
    m("p", "Select an example to display!")
  }

  val component = Component.stateful[State, js.Object](_ => new State) { vnode =>
    import vnode.state

    val default = m(defaultComponent)
    val choices = Seq[(String, VNode)](
      "None" -> default,
      "Hello" -> m(HelloDemo.component),
      "Counter" -> m(CounterDemo.component),
      "Tree" -> m(TreeDemo.component, TreeDemo.Attrs("root")),
      "Data Fetch" -> m(DataFetchDemo.component)
    )

    val choiceList = choices map { case (n, _) =>
      m("option", js.Dynamic.literal("value" -> n), n)
    }

    val selectHandler: js.ThisFunction = (e: dom.raw.HTMLOptionElement) => {
      state.selection() = e.value
    }

    val choiceBox = m("select", js.Dynamic.literal(
      "name" -> "examples",
      "onchange" -> selectHandler
    ), choiceList.toJSArray)

    m.fragment(js.Dynamic.literal(), js.Array[VNode](
      choiceBox,
      choices.collectFirst({ case (n, s) if n == state.selection() => s }).getOrElse(default)
    ))
  }

  protected class State {
    val selection = MithrilStream[String]("None")
  }
}
