package co.technius.scalajs.mithril

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSImport

@JSImport("mithril", JSImport.Namespace)
@js.native
object Mithril extends MithrilHyperscript {

  def render(rootElement: dom.raw.Element, children: VNode.Child): Unit =
    js.native

  def mount(rootElement: dom.raw.Element, component: Component): js.Object =
    js.native

  val route: MithrilRoute = js.native

  def request[T](options: XHROptions[T]): MStream[T] = js.native

  def buildQueryString(`object`: js.Object): String = js.native

  def parseQueryString(string: String): js.Object = js.native

  def withAttr(attr: String, callback: js.Function): js.Function = js.native
  def withAttr(attr: String, callback: MStream[_]): js.Function = js.native

  def trust(string: String): VNode = js.native

  def fragment(attrs: js.Object, children: js.Array[VNode]): VNode.FragmentNode =
    js.native

  def redraw(): Unit = js.native

  def version: String = js.native
}

@js.native
trait MithrilHyperscript extends js.Object {
  import VNode.Child
  def apply(): VNode = js.native
  def apply(selector: String): VNode = js.native
  def apply(selector: String, attributes: js.Object): VNode = js.native
  def apply(selector: String, children: Child): VNode = js.native
  def apply(selector: String, attributes: js.Object, children: Child): VNode = js.native

  def apply(component: Component, attrs: js.Object): VNode = js.native
}
