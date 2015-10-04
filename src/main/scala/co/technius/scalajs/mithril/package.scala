package co.technius.scalajs

import scala.language.implicitConversions
import scala.scalajs.js

package object mithril {
  val m = Mithril

  implicit def xhrOpts[T](options: XHROptions[T]): XHROptionsT[T] = {
    js.use(options).as[XHROptionsT[T]]
  }
}
