import co.technius.scalajs.mithril._
import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import org.scalajs.dom

@ScalaJSDefined
object TreeComponent extends Component[TreeState, TreeAttrs] {
  import helpers._

  override val oninit = js.defined { (vnode: RootNode) =>
    vnode.state = new TreeState
  }

  override def view(vnode: RootNode): VNode = {
    import vnode.state
    val children = state.children.map { c =>
      m("li", m(TreeComponent, TreeAttrs(c)))
    }

    val addChildBtn = m("button", js.Dynamic.literal("onclick" -> helpers.addChild(state) _), "Add child")

    m("div", js.Array[VNode](
      m("span", vnode.attrs.name),
      addChildBtn,
      m("ul", js.Array[VNode](
        m.fragment(new js.Object, children)
      ))
    ))
  }

  object helpers {
    def addChild(state: TreeState)(clickEvent: dom.raw.Event): Unit = {
      val name = dom.window.prompt("Enter child name").trim
      if (!name.isEmpty) {
        state.children += name
      }
    }
  }
}

class TreeState {
  val children: js.Array[String] = js.Array()
}

case class TreeAttrs(name: String)
