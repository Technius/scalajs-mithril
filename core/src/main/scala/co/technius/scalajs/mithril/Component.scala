package co.technius.scalajs.mithril

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation._
import scala.annotation.meta.field

@ScalaJSDefined
trait Component extends js.Object {
  protected type RootNode <: GenericVNode[_, _]
  def view(vnode: RootNode): VNode
}
