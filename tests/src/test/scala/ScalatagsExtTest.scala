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

  "Scalatags" should "compile" in {
    val comp = Component.viewOnly[js.Object] { vnode =>
      div(
        p("foo"),
        p("bar")
      ).render
    }
    mountApp(comp)
  }

  /* Test is failing at the moment -- need to fix it
   *
   * it should "produce output equal to manual creation" in {
   *   val hyperscript =
   *     m("div", js.Array[VNode](
   *       m("p", "foo"),
   *       m("p", "bar")
   *     ))

   *   val tags =
   *     div(
   *       p("foo"),
   *       p("bar")
   *     ).render

   *   hyperscript should === (tags)
   * }
   */
}
