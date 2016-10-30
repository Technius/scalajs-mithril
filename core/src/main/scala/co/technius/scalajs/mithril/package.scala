package co.technius.scalajs

import scala.language.implicitConversions
import scala.scalajs.js

package object mithril {
  val m = Mithril

  type RichPromise[T] = Promise.RichPromise[T]
  type RichMithrilProp[T] = MithrilProp.RichMithrilProp[T]
}
