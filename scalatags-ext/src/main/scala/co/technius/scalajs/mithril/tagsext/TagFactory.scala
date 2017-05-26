package co.technius.scalajs.mithril.tagsext

import co.technius.scalajs.mithril._
import scalatags.generic
import scalatags.generic.Namespace

trait TagFactory extends generic.Util[VNode, VNode, VNode] {
  override def tag(s: String, void: Boolean = false): ConcreteHtmlTag[VNode] = {
    makeAbstractTypedTag[VNode](s, void, Namespace.htmlNamespaceConfig)
  }
}
