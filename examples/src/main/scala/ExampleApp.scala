import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.ScalaJSDefined
import org.scalajs.dom

import co.technius.scalajs.mithril._

object ExampleApp extends js.JSApp {
  def main(): Unit = {
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

    m.fragment(js.Dynamic.literal(), js.Array[VNode](
      choiceBox,
      displaying
    ))
  }

  protected class State {
    val selection = m.prop[String]()

    val choices = Map[String, Component](
      "None" -> null,
      "Hello" -> HelloComponent,
      "Counter" -> CounterComponent,
      "Data Fetch" -> DataFetchComponent
    )
  }
}
