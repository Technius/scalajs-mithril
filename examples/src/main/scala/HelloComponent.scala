import co.technius.scalajs.mithril._
import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import org.scalajs.dom

@ScalaJSDefined
object HelloComponent extends Component {

  type RootNode = GenericVNode[State, _]

  def oninit(vnode: RootNode) = {
    vnode.state = new State
  }

  def view(vnode: RootNode): VNode = {
    import vnode.state
    m("div", js.Array[VNode](
      m("span", s"Hi, ${state.name()}!"),
      m("input[type=text]", js.Dynamic.literal(
        oninput = m.withAttr("value", state.name),
        value = state.name()
      ))
    ))
  }

  protected class State {
    val name = m.prop("Name")
  }
}
