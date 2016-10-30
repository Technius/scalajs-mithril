package co.technius.scalajs.mithril

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.|

@js.native
trait MithrilRoute extends js.Object {

  // Get route
  def apply(): String = js.native

  // Redirect
  def apply(route: String): Unit = js.native
  def apply(route: String, shouldReplaceHistory: Boolean): Unit = js.native
  def apply(route: String, params: js.Any*): Unit = js.native
  def apply(route: String, params: js.Array[js.Any], shouldReplaceHistory: Boolean): Unit = js.native

  // Initialize
  def apply(rootElement: dom.raw.Element, defaultRoute: String, routes: js.Dictionary[Component]): Unit = js.native

  var mode: String = js.native

  def param(key: String): String = js.native

  def buildQueryString(data: js.Object): String = js.native

  def parseQueryString(querystring: String): js.Object = js.native
}

object MithrilRoute {
  @inline implicit class RichMithrilRoute(val route: MithrilRoute) extends AnyVal {
    @inline def apply(rootElement: dom.raw.Element, defaultRoute: String)(routes: (String, Component)*): Unit = {
      route(rootElement, defaultRoute, js.Dictionary(routes: _*))
    }
  }
}
