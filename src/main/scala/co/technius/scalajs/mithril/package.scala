package co.technius.scalajs

import scala.language.implicitConversions
import scala.scalajs.js

package object mithril {
  val m = Mithril

  implicit def xhrOpts[T](options: XHROptions[T]): XHROptionsT[T] = {
    js.use(options).as[XHROptionsT[T]]
  }

  implicit def component2Mithril(c: Component): MithrilComponent =
    js.use(c).as[MithrilComponent]

  type RichPromise[T] = Promise.RichPromise[T]
}
