import co.technius.scalajs.mithril._
import org.scalajs.dom
import scala.scalajs.js

trait TestUtils {
  def mountApp(comp: Component[_, _]): dom.Element = {
    val div = dom.document.createElement("div")
    dom.document.body.appendChild(div)
    m.mount(div, comp)
    div
  }
}
