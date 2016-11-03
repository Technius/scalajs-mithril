package co.technius.scalajs.mithril

import org.scalajs.dom.XMLHttpRequest
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.{ JSExportAll, ScalaJSDefined }

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
