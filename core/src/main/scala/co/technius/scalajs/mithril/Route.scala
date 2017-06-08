package co.technius.scalajs.mithril

import org.scalajs.dom
import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.ScalaJSDefined

import MithrilRoute.Route

@js.native
trait MithrilRoute extends js.Object {

  def get(): String = js.native

  def set(path: String, data: js.Object = ???, options: js.Object = ???): Unit = js.native

  def link(vnode: VNode): js.Function1[VNode, Unit] = js.native

  def apply(root: dom.Element, defaultRoute: String, routes: js.Dictionary[Route]): Unit = js.native

  def prefix(prefix: String): Unit = js.native

  def param(): String = js.native

  def param(key: String): js.Dictionary[js.Any] = js.native

  var mode: String = js.native
}

/**
 * A RouteResolver, as defined in mithril. At least one of the two methods must
 * be defined.
 */
@ScalaJSDefined
abstract class RouteResolver extends js.Object {

  // explicit type annotations for Scala 2.11 support
  // could be implemented in 2.12 using
  //     (_, _) => ()
  def onmatch: js.Function2[js.Dictionary[js.Any], String, RouteResolver.Resolved] =
    (_: js.Dictionary[js.Any], _: String) => (): RouteResolver.Resolved

  // explicit type annotations for Scala 2.11 support
  // could be implemented in 2.12 using
  //     identity
  def render: js.Function1[VNode, VNode] = (vnode: VNode) => vnode
}

object RouteResolver  {

  /**
    * A resolved component returned by onmatch.
    */
  type Resolved = Component[_,_] | js.Promise[Component[_,_]] | Unit

  /**
    * Creates a RouteResolver that contains a render method.
    */
  def render(f: VNode => VNode): Route = new RouteResolver {
    override def render = f
  }

  /**
    * Creates a RouteResolver that contains an onmatch method.
    */
  def onmatch(f: (js.Dictionary[js.Any], String) => Resolved): Route =
    new RouteResolver {
      override def onmatch = f
    }
}

object MithrilRoute {
  type Route = Component[_, _] | RouteResolver
  @inline implicit class MithrilRouteOps(val route: MithrilRoute) extends AnyVal {
    @inline def apply(rootElement: dom.raw.Element, defaultRoute: String)(routes: (String, Route)*): Unit = {
      route(rootElement, defaultRoute, js.Dictionary(routes: _*))
    }
  }
}
