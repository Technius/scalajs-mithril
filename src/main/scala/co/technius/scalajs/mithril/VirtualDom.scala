package co.technius.scalajs.mithril

import scala.scalajs.js

@js.native
trait VirtualDom extends js.Object {
  val tag: String = js.native
  val attrs: js.Object = js.native
  val children: js.Array[VirtualDom] = js.native
}

class VirtualDomChild[T]
object VirtualDomChild {
  implicit object DomString extends VirtualDomChild[String]
  implicit object DomSeq extends VirtualDomChild[js.Array[VirtualDomChild[_]]]
  implicit object DomChild extends VirtualDomChild[VirtualDomChild[_]]
  implicit object DomComponent extends VirtualDomChild[ViewComponent]
}
