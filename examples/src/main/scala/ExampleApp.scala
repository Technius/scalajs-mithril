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
object ShowcaseComponent extends Component[ShowcaseState, js.Object] {

  override val oninit = js.defined { (vnode: RootNode) =>
    vnode.state = new ShowcaseState
  }

  def view(vnode: RootNode) = {
    import vnode.state

    val choices = Seq[(String, VNode)](
      "None" -> m(BlankComponent),
      "Hello" -> m(HelloComponent),
      "Counter" -> m(CounterComponent),
      "Tree" -> m(TreeComponent, TreeAttrs("root")),
      "Data Fetch" -> m(DataFetchComponent),
      "Builder" -> m(BuilderDemo.component)
    )

    val displaying =
      state.selection.toOption
        .flatMap(choices.toMap.get(_))
        .getOrElse(m("p", "Select an example to display!"))

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
      displaying
    ))
  }
}

protected class ShowcaseState {
  val selection = MithrilStream[String]()
}

@ScalaJSDefined
object BlankComponent extends Component[js.Object, js.Object] {
  override def view(node: RootNode) = m.fragment(new js.Object, js.Array())
}
