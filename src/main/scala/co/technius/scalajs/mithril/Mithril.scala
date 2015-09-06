package co.technius.scalajs.mithril

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("m")
object Mithril extends Mithril {
  def prop[A](): MithrilProp[A] = js.native
  def prop[A](value: A): MithrilProp[A] = js.native

  val route: MithrilRoute = js.native
}

trait Mithril extends js.Object {
  private[this] type Child[T] = VirtualDomChild[T]
  def apply(): VirtualDom = js.native
  def apply(selector: String): VirtualDom = js.native
  def apply(selector: String, attributes: js.Object): VirtualDom = js.native
  def apply[T: Child](selector: String, children: T): VirtualDom = js.native
  def apply[T: Child](selector: String, attributes: js.Object, children: T): VirtualDom = js.native

  def mount(rootElement: dom.raw.Element, component: MithrilComponent): js.Object = js.native

  def withAttr(attr: String, callback: js.Function): js.Function = js.native
  def withAttr(attr: String, callback: MithrilProp[_]): js.Function = js.native

  def component(component: MithrilComponent, args: js.Any*): js.Object = js.native
}

trait MithrilProp[A] extends js.Object {
  def apply(): A = js.native
  def apply(value: A): A = js.native
}

trait MithrilComponent extends js.Object {
  val controller: js.UndefOr[js.Function] = js.native
  val view: js.Function = js.native
}
