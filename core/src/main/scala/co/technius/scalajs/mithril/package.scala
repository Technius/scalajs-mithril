package co.technius.scalajs

import scala.language.implicitConversions
import scala.scalajs.js

package object mithril {
  val m = Mithril

  type RichMStream[T] = MStream.RichMStream[T]
}
