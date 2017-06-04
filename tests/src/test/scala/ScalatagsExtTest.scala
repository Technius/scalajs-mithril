import co.technius.scalajs.mithril._
import co.technius.scalajs.mithril.VNodeScalatags.{ attrs => *, tags => t }
import co.technius.scalajs.mithril.VNodeScalatags.implicits._
import org.scalatest._
import org.scalajs.dom
import scala.scalajs.js

class ScalatagsExtTest extends FlatSpec with Matchers with TestUtils{

  "The bundle" should "render" in {
    val comp = Component.viewOnly[js.Object] { vnode =>
      t.div(
        t.a(*.href := "/example"),
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
      t.div(
        t.span(*.cls := "foo"),
        t.span(*.cls := "bar baz")
      ).render
    }
    val node = mountApp(comp)

    val children = node.firstElementChild.children
    val span1 = children(0).asInstanceOf[dom.html.Span]
    val span2 = children(1).asInstanceOf[dom.html.Span]

    def hasClass(cls: String, elem: dom.html.Element) =
      withClue(s"Should have class $cls:") {
        elem.classList.contains(cls) should be (true)
      }

    hasClass("foo", span1)
    hasClass("bar", span2)
    hasClass("baz", span2)
  }

  it should "handle style properly" in {
    val comp = Component.viewOnly[js.Object] { vnode =>
      t.div(
        t.span(*.style := "color: red; font-family: serif")("Red text"),
        t.span(*.css("font-size") := 2.em, *.css("font-style") := "italic")("2em text")
      ).render
    }
    val node = mountApp(comp)

    val children = node.firstElementChild.children
    val span1 = children(0).asInstanceOf[dom.html.Span]
    val span2 = children(1).asInstanceOf[dom.html.Span]

    span1.style.color should be ("red")
    span1.style.fontFamily should be ("serif")

    span2.style.fontSize should be ("2em")
    span2.style.fontStyle should be ("italic")
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

  it should "support lifecycle methods" in {
    var triggered = false
    val comp = Component.viewOnly[js.Object] { vnode =>
      val initFn = () => {
        triggered = true
      }
      t.div(*.oninit := initFn).render
    }
    val node = mountApp(comp)
    withClue("oninit not fired:") { triggered should be (true) }
  }
}
