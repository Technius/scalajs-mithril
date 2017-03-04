import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

import co.technius.scalajs.mithril._

@ScalaJSDefined
object DataFetchComponent extends Component {

  type RootNode = GenericVNode[State]
  def oninit(vnode: RootNode): Unit = {
    vnode.state = new State
  }

  def view(vnode: RootNode) = {
    import vnode.state
    import helpers._
    val fetchBtn = m("button", js.Dynamic.literal(
      "onclick" -> (() => loadData(state))
    ), "Fetch sample-data.json!")

    m("div", js.Array[VNode](
      fetchBtn,
      m("p", "sample-data.json:"),
      m("p", state.data.toOption.map(js.JSON.stringify(_)).getOrElse(""))
    ))
  }

  protected class State {
    val data = MithrilStream[js.Object]()
  }

  object helpers {
    def loadData(state: State): Unit = {
      val url = "../../../src/main/resources/sample-data.json"
      val opts = new XHROptions[js.Object](method = "GET", url = url)

      val req = m.request(opts)
      req foreach { data =>
        state.data() = data
      }
    }
  }
}
