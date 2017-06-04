package co.technius.scalajs.mithril.tagsext

import co.technius.scalajs.mithril.VNode
import scalatags.generic

trait Attrs extends generic.Attrs[VNode, VNode, VNode] {
  val key = attr("key")
  val oninit = attr("oninit")
  val oncreate = attr("oncreate")
  val onupdate = attr("onupdate")
  val onbeforeremove = attr("onbeforeremove")
  val onremove = attr("onremove")
  val onbeforeupdate = attr("onbeforeupdate")
}
