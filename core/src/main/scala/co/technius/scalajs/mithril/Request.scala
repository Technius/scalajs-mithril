package co.technius.scalajs.mithril

import org.scalajs.dom.XMLHttpRequest
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSExportAll, ScalaJSDefined }

@js.native
trait Deferred[T] extends js.Object {

  var onerror: js.Function = js.native

  val promise: Promise[T] = js.native

  def resolve(value: T): Unit = js.native

  def reject(value: Any): Unit = js.native
}

@js.native
trait Promise[T] extends js.Object with MithrilProp[T] {
  def `then`[R](successCallback: js.Function1[T, R]): Promise[R] = js.native
  def `then`[R](successCallback: js.Function1[T, R], errorCallback: js.Function): Promise[R] = js.native
}

object Promise {
  implicit class RichPromise[T](val wrap: Promise[T]) extends AnyVal {
    @inline def map[R](f: T => R): Promise[R] = wrap.`then`(f)
    @inline def foreach(f: T => Unit): Unit = wrap.`then`(f)
    @inline def onSuccess(f: PartialFunction[T, Unit]): Unit = wrap.`then`(f)
    @inline def onFailure[E](f: PartialFunction[E, Unit]): Unit = wrap.`then`(null, f)
    @inline def recover[U >: T](f: PartialFunction[Any, U]): Promise[U] = wrap.`then`(null, f)

    @inline def flatMap[R](f: T => Promise[R]): Promise[R] = {
      val d = m.deferred[R]()
      wrap foreach { a =>
        f(a) foreach { b =>
          d.resolve(b)
        }
      }
      d.promise
    }
  }
}

@ScalaJSDefined
class XHROptions[T](
  var method: String,
  var url: String,
  var user: js.UndefOr[String] = js.undefined,
  var password: js.UndefOr[String] = js.undefined,
  var data: js.UndefOr[js.Object] = js.undefined,
  var background: js.UndefOr[Boolean] = js.undefined,
  var initialValue: js.UndefOr[Any] = js.undefined,
  var unwrapSuccess: js.UndefOr[js.Function2[js.Any, XMLHttpRequest, T]] = js.undefined,
  var unwrapError: js.UndefOr[js.Function2[js.Any, XMLHttpRequest, Any]] = js.undefined,
  var serialize: js.UndefOr[js.Function1[T, String]] = js.undefined,
  var deserialize: js.UndefOr[js.Function1[String, T]] = js.undefined) extends js.Object
