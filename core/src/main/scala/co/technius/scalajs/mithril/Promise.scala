package co.technius.scalajs.mithril

import scalajs.js
import scalajs.js.annotation.JSName

@js.native
object Promise extends js.Object {
  def resolve[T](value: T): Promise[T] = js.native

  def reject[T](value: Any): Promise[T] = js.native

  @JSName("all")
  /**
    * A one-arity (with respect to types) version of `Promise.all`.
    */
  def sequence[T](ps: js.Array[Promise[T]]): Promise[js.Array[T]] = js.native

  @JSName("race")
  /**
    * A one-arity (with respect to types) version of `Promise.race`.
    */
  def firstOf[T](ps: js.Array[Promise[T]]): Promise[T] = js.native

  @JSName("resolve")
  /**
    * An alias for `Promise.resolve`
    */
  def apply[T](value: T): Promise[T] = js.native
}

@js.native
trait Promise[+T] extends js.Object {

  @JSName("then")
  /**
    * An alias for `Promise.then`, with one caveat: `U` cannot be a `Promise[U]`.
    * According to the [[https://mithril.js.org/promise.html#promisethen mithril documentation]],
    * `then` will automatically flatten any `Promise` returned by `f`.
    */
  def map[U](f: js.Function1[T, U]): Promise[U] = js.native

  @JSName("then")
  /**
    * An alias for `Promise.then`.
    */
  def flatMap[U](f: js.Function1[T, Promise[U]]): Promise[U] = js.native

  @JSName("then")
  /**
    * An alias for `Promise.then`
    */
  def foreach(f: js.Function1[T, Unit]): Unit = js.native

  @JSName("catch")
  /**
    * An alias for `Promise.then`, with one caveat: `U` cannot be a `Promise[U]`.
    * According to the [[https://mithril.js.org/promise.html#promisethen mithril documentation]],
    * `recover` will automatically flatten any `Promise` returned by `f`.
    */
  def recover[U](f: js.Function1[Any, U]): Promise[U] = js.native

  @JSName("catch")
  /**
    * An alias for `Promise.catch`
    */
  def recoverWith[U](f: js.Function1[Any, Promise[U]]): Promise[U] = js.native
}
