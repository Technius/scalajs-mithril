import co.technius.scalajs.mithril._
import org.scalatest._
import scala.scalajs.js
import co.technius.scalajs.mithril.VNodeScalatags.{ tags => t }
import co.technius.scalajs.mithril.VNodeScalatags.attrs._
import co.technius.scalajs.mithril.VNodeScalatags.implicits._

class ScalatagsExtTest extends FlatSpec with Matchers with TestUtils{

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

  it should "handle classes properly" in {
    val comp = Component.viewOnly[js.Object] { vnode =>
      t.div(cls := "foo bar").render
    }
    val node = mountApp(comp)
    val classes = node.firstElementChild.classList
    classes.contains("foo") should be (true)
    classes.contains("bar") should be (true)
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
