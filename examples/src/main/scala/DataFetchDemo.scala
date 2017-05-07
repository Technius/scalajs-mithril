import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

import co.technius.scalajs.mithril._

object DataFetchDemo {

  val component = Component.stateful[State, js.Object](_ => new State) { vnode =>
    import vnode.state
    val fetchBtn = m("button", js.Dynamic.literal(
      "onclick" -> (() => loadData(state))
    ), "Fetch sample-data.json!")

    m("div", js.Array[VNode](
      fetchBtn,
      m("p", "sample-data.json:"),
      m("p", state.data.toOption.map(js.JSON.stringify(_)).getOrElse(""))
    ))
  }

  def loadData(state: State): Unit = {
    val url = "../../../src/main/resources/sample-data.json"
    val opts = new XHROptions[js.Object](method = "GET", url = url)

    val req = m.request(opts)
    req.toFuture foreach { data =>
      state.data() = data
    }
  }

  protected class State {
    val data = MithrilStream[js.Object]()
  }
}
