package co.technius.scalajs.mithril

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSImport

@JSImport("mithril", JSImport.Namespace)
@js.native
object Mithril extends MithrilCore with MithrilRendering with MithrilHtml {

  def request[T](options: XHROptions[T]): MStream[T] = js.native

  val route: MithrilRoute = js.native

  def buildQueryString(`object`: js.Object): String = js.native

  def parseQueryString(string: String): js.Object = js.native
}

@js.native
trait MithrilCore extends js.Object {
  import VNode.Child
  def apply(): VNode = js.native
  def apply(selector: String): VNode = js.native
  def apply(selector: String, attributes: js.Object): VNode = js.native
  def apply(selector: String, children: Child): VNode = js.native
  def apply(selector: String, attributes: js.Object, children: Child): VNode = js.native

  def mount(rootElement: dom.raw.Element, component: Component): js.Object = js.native

  def withAttr(attr: String, callback: js.Function): js.Function = js.native
  def withAttr(attr: String, callback: MStream[_]): js.Function = js.native

  def apply(component: Component, args: js.Any*): VNode = js.native

  def fragment(attrs: js.Object, children: js.Array[VNode]): VNode = js.native
}

@js.native
trait MithrilRendering extends js.Object {
  def render(rootElement: dom.raw.Element, children: VNode.Child,
             forceRecreation: Boolean = ???): Unit = js.native

  def redraw(): Unit = js.native
  def redraw(forceSync: Boolean): Unit = js.native
  def startComputation(): Unit = js.native
  def endComputation(): Unit = js.native
}

@js.native
trait MithrilHtml extends js.Object {
  // Must return js.Object, since returning String causes a runtime error
  def trust(string: String): js.Object = js.native
}
