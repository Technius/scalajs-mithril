import scala.scalajs.js
import scala.scalajs.js.Dynamic.{ literal => json }
import scala.scalajs.js.JSConverters._
import org.scalajs.dom

import co.technius.scalajs.mithril._

object ExampleApp extends js.JSApp {
  def main(): Unit = {
    m.mount(dom.document.getElementById("app"), ShowcaseComponent)
  }
}

object ShowcaseComponent extends Component {

  val choices = Map[String, MithrilComponent](
    "None" -> null,
    "Counter" -> CounterComponent
  )

  override val controller: js.Function = () => new ShowcaseCtrl

  val view: js.Function = (ctrl: ShowcaseCtrl) => {
    val compOpt: Option[MithrilComponent] = for {
      cname <- ctrl.selection.toOption
      comp <- choices.get(cname) if comp != null
    } yield comp

    val displaying: VirtualDom = compOpt match {
      case Some(x) => m.component(x)
      case None => m("p", "Select an example to display!")
    }

    val choiceList = choices.keys map { n =>
      m("option", js.Dynamic.literal("value" -> n), n)
    }

    val selectHandler: js.ThisFunction = (e: dom.raw.HTMLOptionElement) => {
      ctrl.selection() = e.value
    }

    val choiceBox = m("select", js.Dynamic.literal(
      "name" -> "examples",
      "onchange" -> selectHandler
    ), choiceList.toJSArray)

    js.Array[VirtualDom](
      choiceBox,
      m("div", displaying)
    )
  }

  private[this] class ShowcaseCtrl {
    val selection = m.prop[String]()
  }
}
