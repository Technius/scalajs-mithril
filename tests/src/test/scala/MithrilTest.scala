import co.technius.scalajs.mithril._
import org.scalatest._
import org.scalajs.dom
import scala.scalajs.js

class MithrilTest extends FlatSpec with Matchers {

  def mountApp(comp: Component[_, _]): Unit = {
    val div = dom.document.createElement("div")
    dom.document.body.appendChild(div)
    m.mount(div, comp)
  }

  "The facade" should "work" in {
    // if the mithril functions don't work, this test will fail
    m.version shouldNot be (js.undefined)


    val comp = Component.viewOnly[js.Object] { vnode =>
      m("div", "test")
    }
    mountApp(comp)
  }

  it should "mount components successfully" in {
    val comp = Component.viewOnly[js.Object] { vnode =>
      m("div", js.Array(
        m("span", js.Dynamic.literal(id = "should-appear"))
      ))
    }
    mountApp(comp)
    dom.document.getElementById("should-appear") shouldNot be (null)
  }

  it should "support streams" in {
    val s = MithrilStream[Int](5)
    s() shouldBe (5)
    val child = s.map(_ + 5)
    child() shouldBe (10)
    s() = 10
    child() shouldBe(15)
  }
}
