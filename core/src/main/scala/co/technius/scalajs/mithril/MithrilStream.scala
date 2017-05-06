package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSImport, JSName }

@js.native
@JSImport("mithril/stream", JSImport.Namespace)
object MithrilStream extends js.Object {

  def apply[T](): MStream[T] = js.native

  def apply[T](value: T): MStream[T] = js.native

  /**
    * The type signature of this function cannot be written easily in Scala
    */
  def combine(combiner: js.Function, streams: js.Array[MStream[Any]]): MStream[Any] = js.native

  /**
    * `merge` is like `sequence` on Scala collection types except that it is
    * parameterized on `Any`. Prefer `sequence` (defined as an alias) whenever
    * possible.
    */
  def merge(streams: js.Array[MStream[Any]]): MStream[js.Array[Any]] = js.native

  /**
    * Type-safe alias for merge.
    */
  @JSName("merge")
  def sequence[T](streams: js.Array[MStream[T]]): MStream[js.Array[T]] = js.native

  /**
    * Mithril's version of a `fold`. Use the `fold` method on `MStreamOps` for more
    * familiar syntax.
    */
  def scan[A, B](f: js.Function2[B, A, B], accumulator: B, stream: MStream[A]): MStream[B] = js.native

  def scanMerge[A, B](pairs: js.Array[(MStream[A], js.Function2[B, A, B])], acc: B): MStream[B] = js.native

  def HALT: js.Any = js.native
}

@js.native
trait MStream[+T] extends js.Object {

  def apply(): T = js.native
  def apply[U >: T](value: U): U = js.native

  def end: MStream[Boolean] = js.native

  @JSName("fantasy-land/map")
  def map[U](f: js.Function1[T, U]): MStream[U] = js.native

  @JSName("fantasy-land/ap")
  def ap[U](f: MStream[js.Function1[T, U]]): MStream[U] = js.native
}

object MStream {
  implicit class MStreamOps[T](val wrap: MStream[T]) extends AnyVal {

    /**
     * Syntax sugar for [[co.technius.scalajs.mithril.MStream!.apply(*]]
     * @example {{{
     * val count = MithrilStream[Int](0)
     * count() = 1
     * println(count()) // 1
     * }}}
     */
    @inline def update(value: T): T = wrap(value)

    /**
     * Calls `f` with the current value and sets the value of the
     * [[co.technius.scalajs.mithril.MithrilStream]] to the result of the function
     * call.
     *
     * @example {{{
     * val count = MithrilStream[Int](0)
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

    /**
      * Alias for `scan`.
      */
    @inline def fold[U](acc: U)(f: (U, T) => U): MStream[U] =
      MithrilStream.scan(f, acc, wrap)
  }
}
