package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSImport, JSName }

@js.native
@JSImport("mithril/stream", JSImport.Namespace)
object MithrilStream extends js.Object {

  def apply[T](): MStream[T] = js.native

  def apply[T](value: T): MStream[T] = js.native

  /**
    * Not type-safe
    */
  def combine(combiner: js.Function, streams: js.Array[MStream[Any]]): MStream[Any] = js.native

  def reject[T](value: Any): MStream[T] = js.native

  def merge(streams: js.Array[MStream[Any]]): MStream[js.Array[Any]] = js.native

  @JSName("merge")
  /**
    * Type-safe alias for merge.
    */
  def sequence[T](streams: js.Array[MStream[T]]): MStream[js.Array[T]] = js.native

  def HALT: js.Any = js.native
}

@js.native
trait MStream[+T] extends js.Object {

  def run[U](callback: js.Function1[T, U]): MStream[U] = js.native

  def apply(): T = js.native
  def apply[U >: T](value: U): U = js.native

  def end: MStream[Boolean] = js.native

  def error: MStream[Any] = js.native

  def `catch`[U](callback: js.Function1[Any, U]): MStream[U] = js.native

  @JSName("fantasy-land/map")
  def map[U](f: js.Function1[T, U]): MStream[U] = js.native

  @JSName("fantasy-land/ap")
  def ap[U](f: MStream[js.Function1[T, U]]): MStream[U] = js.native
}

object MStream {
  implicit class MStreamOps[T](val wrap: MStream[T]) extends AnyVal {

    /**
     * Syntax sugar for [[MStream!.apply(value* MStream.apply(value)]]
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
