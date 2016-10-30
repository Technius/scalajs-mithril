import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.ScalaJSDefined
import org.scalajs.dom

import co.technius.scalajs.mithril._

object ExampleApp extends js.JSApp {
  def main(): Unit = {
    dom.console.log(m("div"))
    val comp = new Component {
      type RootNode = GenericVNode[State, _]
      def oninit(node: RootNode) = {
        node.state = new State
      }
      def view(node: RootNode) = {
        val state = node.state
        m("div", js.Array[VNode](
          m("h1", "Foo"),
          m("p", "bar"),
          m("p", s"Count: ${state.count}"),
          m("button", js.Dynamic.literal("onclick" -> state.inc), "Increase")
        ))
      }
      class State {
        var count: Int = 0
        val inc: js.Function = { () =>
          count = count + 1
        }
      }
    }
    // m.mount(dom.document.getElementById("app"), comp)
    m.mount(dom.document.getElementById("app"), ShowcaseComponent)
  }
}

@ScalaJSDefined
object ShowcaseComponent extends Component {

  type RootNode = GenericVNode[State, _]

  def oninit(vnode: RootNode) = {
    vnode.state = new State
  }

  def view(vnode: RootNode) = {
    import vnode.state
    val compOpt: Option[Component] = for {
      cname <- state.selection.toOption
      comp <- state.choices.get(cname) if comp != null
    } yield comp

    val displaying: VNode = compOpt match {
      case Some(x) => m(x)
      case None => m("p", "Select an example to display!")
    }

    val choiceList = state.choices.keys map { n =>
      m("option", js.Dynamic.literal("value" -> n), n)
    }

    val selectHandler: js.ThisFunction = (e: dom.raw.HTMLOptionElement) => {
      state.selection() = e.value
    }

    val choiceBox = m("select", js.Dynamic.literal(
      "name" -> "examples",
      "onchange" -> selectHandler
    ), choiceList.toJSArray)

    m("div", js.Array[VNode](
      choiceBox,
      m("div", displaying)
    ))
  }

  protected class State {
    val selection = m.prop[String]()

    val choices = Map[String, Component](
      "None" -> null,
      "Counter" -> CounterComponent,
      "Data Fetch" -> DataFetchComponent
    )
  }
}
