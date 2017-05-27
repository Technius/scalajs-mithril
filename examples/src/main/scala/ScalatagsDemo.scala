import co.technius.scalajs.mithril._
import co.technius.scalajs.mithril.VNodeScalatags.all._
import org.scalajs.dom
import scala.scalajs.js

object ScalatagsDemo {

  val component = Component.stateful[State, js.Object](_ => new State) { vnode =>
    import vnode.state
    div(
      span(s"Hello from scalatags, ${state.name()}!"),
      input(
        `type` := "text",
        value := state.name(),
        oninput := m.withAttr("value", state.name)
      ),
      embeddedComponent
    ).render
  }

  val embeddedComponent = Component.viewOnly[js.Object] { vnode =>
    div(
      p("It's possible to embed components in the tags, too! Let's embed the Counter demo!"),
      CounterDemo.component
    ).render
  }

  protected class State {
    val name = MithrilStream("Name")
  }
}
