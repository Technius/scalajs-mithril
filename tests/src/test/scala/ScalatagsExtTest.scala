import co.technius.scalajs.mithril._
import org.scalatest._
import org.scalajs.dom
import scala.scalajs.js
import co.technius.scalajs.mithril.VNodeScalatags.{ tags => t }
import co.technius.scalajs.mithril.VNodeScalatags.attrs._
import co.technius.scalajs.mithril.VNodeScalatags.implicits._

class ScalatagsExtTest extends FlatSpec with Matchers {

  def mountApp(comp: Component[_, _]): Unit = {
    val div = dom.document.createElement("div")
    dom.document.body.appendChild(div)
    m.mount(div, comp)
  }

  "The bundle" should "compile" in {
    val comp = Component.viewOnly[js.Object] { vnode =>
      t.div(
        t.a(href := "/example"),
        t.p("foo"),
        t.p("bar"),
        t.div(
          t.p("baz")
        )
      ).render
    }
    mountApp(comp)
  }

  it should "allow embedding of components in tags" in {
    val embedded = Component.viewOnly[js.Object] { vnode =>
      t.p("Embedded").render
    }
    val comp = Component.viewOnly[js.Object] { vnode =>
      t.div(
        "Test",
        embedded
      ).render
    }
    mountApp(comp)
  }
}
