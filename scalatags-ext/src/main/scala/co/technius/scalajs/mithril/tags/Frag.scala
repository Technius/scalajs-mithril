package co.technius.scalajs.mithril.tags

import co.technius.scalajs.mithril.VNode
import scalatags.generic

// Adapted from scalatags
trait Frag extends generic.Frag[VNode, VNode.Child] {
  override def render: VNode
  override def applyTo(parent: VNode): Unit = parent.children += this.render
}
