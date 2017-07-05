package co.technius.scalajs.mithril

import org.scalajs.dom.XMLHttpRequest
import scala.concurrent.Future
import scala.scalajs.js

class XHROptions[T](
  var url: String,
  var method: js.UndefOr[String] = js.undefined,
  var data: js.UndefOr[js.Object] = js.undefined,
  var async: js.UndefOr[Boolean] = js.undefined,
  var user: js.UndefOr[String] = js.undefined,
  var password: js.UndefOr[String] = js.undefined,
  var withCredentials: js.UndefOr[Boolean] = js.undefined,
  var config: js.UndefOr[js.Function1[XMLHttpRequest, XMLHttpRequest]] = js.undefined,
  var headers: js.UndefOr[js.Object] = js.undefined,
  var `type`: js.UndefOr[js.Function1[js.Any, T]] = js.undefined,
  var serialize: js.UndefOr[js.Function1[T, String]] = js.undefined,
  var deserialize: js.UndefOr[js.Function1[String, T]] = js.undefined,
  var extract: js.UndefOr[js.Function2[XMLHttpRequest, XHROptions[T], T]] = js.undefined,
  var useBody: js.UndefOr[Boolean] = js.undefined,
  var background: js.UndefOr[Boolean] = js.undefined) extends js.Object
