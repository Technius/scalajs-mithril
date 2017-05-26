import co.technius.scalajs.mithril._
import org.scalatest._
import org.scalajs.dom
import scala.scalajs.js
import co.technius.scalajs.mithril.VNodeScalatags.all._

class ScalatagsExtTest extends FlatSpec with Matchers {

  def mountApp(comp: Component[_, _]): Unit = {
    val div = dom.document.createElement("div")
    dom.document.body.appendChild(div)
    m.mount(div, comp)
  }

  "The bundle" should "compile" in {
    val comp = Component.viewOnly[js.Object] { vnode =>
      div(
        VNodeScalatags.tags.a(href := "/example"),
        p("foo"),
        p("bar"),
        div(
          p("baz")
        )
      ).render
    }
    mountApp(comp)
  }
}
