import co.technius.scalajs.mithril._
import co.technius.scalajs.mithril.VNodeScalatags.all._
import org.scalajs.dom
import scala.scalajs.js

object ScalatagsDemo {

  val component = Component.stateful[State, js.Object](_ => new State) { vnode =>
    import vnode.state
    val r = div(
      p("Hello world!"),
      p("Blargh")
    ).render
    dom.console.log(r)
    r
  }

  protected class State {
    val name = MithrilStream("Name")
  }
}
