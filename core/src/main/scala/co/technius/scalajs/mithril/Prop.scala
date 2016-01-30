package co.technius.scalajs.mithril

import scala.scalajs.js

@js.native
trait MithrilProp[T] extends js.Object {
  def apply(): T = js.native
  def apply(value: T): T = js.native
}

object MithrilProp {
  implicit class RichMithrilProp[T](val wrap: MithrilProp[T]) extends AnyVal {

    /**
     * Syntax sugar for [[MithrilProp!.apply(value* MithrilProp.apply(value)]]
     * @example {{{
     * val count = m.prop(0)
     * count() = 1
     * println(count()) // 1
     * }}}
     */
    @inline def update(value: T): T = wrap(value)

    /**
     * Calls `f` with the current value and sets the value of the
     * [[MithrilProp]] to the result of the function call.
     *
     * @example {{{
     * val count = m.prop(0)
     * count() = _ + 1
     * println(count()) // 1
     * }}}
     */
    @inline def update(f: T => T): T = wrap(f(wrap()))

    /**
     * Returns `None` if the value is `undefined` or
     * a `Some` containing the value.
     */
    @inline def toOption: Option[T] = {
      val v = wrap()
      if (v == js.undefined) None else Some(v)
    }
  }
}
