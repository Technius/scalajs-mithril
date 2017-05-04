package co.technius.scalajs.mithril

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.ScalaJSDefined

@js.native
trait MithrilRoute extends js.Object {
  import MithrilRoute.Route

  def get(): String = js.native

  def set(path: String, data: js.Object, options: js.Object): Unit = js.native

  def link(vnode: VNode): js.Function1[dom.Event, Unit] = js.native

  def apply(root: dom.Element, defaultRoute: String, routes: js.Dictionary[Route]): Unit = js.native

  def prefix(prefix: String): Unit = js.native

  var mode: String = js.native
}

/**
 * A RouteResolver, as defined in mithril. At least one of the two methods must
 * be defined.
 */
@ScalaJSDefined
abstract class RouteResolver extends js.Object {
  def onmatch(resolve: js.Function1[Component[_, _], Unit], args: js.Object,
      requestPath: String): Unit = ()

  def render(vnode: VNode): VNode = vnode
}

object MithrilRoute {
  type Route = Component[_, _] | RouteResolver
  @inline implicit class RichMithrilRoute(val route: MithrilRoute) extends AnyVal {
    @inline def apply(rootElement: dom.raw.Element, defaultRoute: String)(routes: (String, Route)*): Unit = {
      route(rootElement, defaultRoute, js.Dictionary(routes: _*))
    }
  }
}
