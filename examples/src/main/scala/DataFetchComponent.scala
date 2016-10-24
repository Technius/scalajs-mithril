import scala.scalajs.js

import co.technius.scalajs.mithril._

object DataFetchComponent extends Component {

  override val controller: js.Function = () => new Controller

  val view: js.Function = (ctrl: Controller) => {
    val fetchBtn = m("button", js.Dynamic.literal(
      "onclick" -> (() => loadData(ctrl))
    ), "Fetch sample-data.json!")

    m("div", js.Array[VirtualDom](
      fetchBtn,
      m("p", "sample-data.json:"),
      m("p", ctrl.data.toOption.map(js.JSON.stringify(_)).getOrElse(""))
    ))
  }

  def loadData(ctrl: Controller): Unit = {
    val url = "../../../src/main/resources/sample-data.json"
    val opts = XHROptions[js.Object](method = "GET", url = url)

    m.request(opts).foreach { data =>
      ctrl.data() = data
    }
  }

  private[this] class Controller {
    val data = m.prop[js.Object]()
  }
}
