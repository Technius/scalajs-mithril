package co.technius.scalajs.mithril

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSImport

@JSImport("mithril", JSImport.Namespace)
@js.native
object Mithril extends MithrilCore with MithrilRendering with MithrilHtml {
  def prop[A](): MithrilProp[A] = js.native
  def prop[A](value: A): MithrilProp[A] = js.native

  def deferred[T](): Deferred[T] = js.native
  def request[T](options: XHROptions[T]): Promise[T] = js.native

  val route: MithrilRoute = js.native
}

@js.native
trait MithrilCore extends js.Object {
  import VirtualDom.Child
  def apply(): VirtualDom = js.native
  def apply(selector: String): VirtualDom = js.native
  def apply(selector: String, attributes: js.Object): VirtualDom = js.native
  def apply(selector: String, children: Child): VirtualDom = js.native
  def apply(selector: String, attributes: js.Object, children: Child): VirtualDom = js.native

  def mount(rootElement: dom.raw.Element, component: MithrilComponent): js.Object = js.native

  def withAttr(attr: String, callback: js.Function): js.Function = js.native
  def withAttr(attr: String, callback: MithrilProp[_]): js.Function = js.native

  def apply(component: MithrilComponent, args: js.Any*): VirtualDom = js.native
  def component(component: MithrilComponent, args: js.Any*): VirtualDom = js.native
}

@js.native
trait MithrilRendering extends js.Object {
  def render(rootElement: dom.raw.Element, children: VirtualDom.Child,
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
