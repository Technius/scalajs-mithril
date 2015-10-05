package co.technius.scalajs.mithril

import org.scalajs.dom.XMLHttpRequest
import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSExportAll, ScalaJSDefined }

@js.native
trait Promise[T] extends js.Object with MithrilProp[T] {
  def `then`[R](successCallback: js.Function1[T, R]): Promise[R] = js.native
  def `then`[R](successCallback: js.Function1[T, R], errorCallback: js.UndefOr[js.Function] = js.undefined): Promise[R] = js.native
}

@js.native
sealed trait RequestOptions extends js.Object

@js.native
trait XHROptionsT[T] extends RequestOptions {
  val method: String = js.native
  val url: String = js.native
  val user: js.UndefOr[String] = js.native
  val password: js.UndefOr[String] = js.native
  val data: js.UndefOr[js.Object] = js.native
  val background: js.UndefOr[Boolean] = js.native

  val initialValue: js.UndefOr[Any] = js.native
  val unwrapSuccess: js.UndefOr[js.Function2[js.Any, XMLHttpRequest, T]] = js.native
  val unwrapError: js.UndefOr[js.Function2[js.Any, XMLHttpRequest, Any]] = js.native

  val serialize: js.UndefOr[js.Function1[T, String]] = js.native
  val deserialize: js.UndefOr[js.Function1[String, T]] = js.native
}

@JSExportAll
case class XHROptions[T](
  method: String,
  url: String,
  user: js.UndefOr[String] = js.undefined,
  password: js.UndefOr[String] = js.undefined,
  data: js.UndefOr[js.Object] = js.undefined,
  background: js.UndefOr[Boolean] = js.undefined,
  initialValue: js.UndefOr[Any] = js.undefined,
  unwrapSuccess: js.UndefOr[js.Function2[js.Any, XMLHttpRequest, T]] = js.undefined,
  unwrapError: js.UndefOr[js.Function2[js.Any, XMLHttpRequest, Any]] = js.undefined,
  serialize: js.UndefOr[js.Function1[T, String]] = js.undefined,
  deserialize: js.UndefOr[js.Function1[String, T]] = js.undefined)
