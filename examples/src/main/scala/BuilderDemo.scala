import co.technius.scalajs.mithril._
import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import org.scalajs.dom

object BuilderDemo {
  val component = Component.viewOnly[Any] { vnode =>
    m("div", js.Array[VNode](
      m("p", "Built using Component.viewOnly"),
      m(buttonComponent)
    ))
  }

  val buttonComponent = Component.build(new BtnState) { state =>
    val clickBtn = { () =>
      state.count.update(_ + 1)
      dom.window.alert("This button has been clicked " + state.count() + " times.")
    }
    m("button", js.Dynamic.literal(onclick = clickBtn), "Click me!")
  }

  protected class BtnState {
    val count = MithrilStream(0)
  }
}
