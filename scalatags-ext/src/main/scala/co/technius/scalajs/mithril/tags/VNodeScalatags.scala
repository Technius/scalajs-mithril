package co.technius.scalajs.mithril.tags

import co.technius.scalajs.mithril._
import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scalatags.{ generic, stylesheet }

// Adapted from scalatags
object VNodeScalatags extends generic.Bundle[VNode, VNode, VNode]
    with generic.Aliases[VNode, VNode, VNode] {

  override object attrs extends Cap with Attrs
  // override object tags extends Cap with Tags
  // override object tags2 extends Cap with Tags2

  object all extends Cap
      with Attrs
      with Tags

  trait Aggregate extends generic.Aggregate[VNode, VNode, VNode] {
    implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier {
      def applyTo(n: VNode): Unit = {
      }
    }
  }

  trait Cap extends Util with TagFactory { self =>

    override type ConcreteHtmlTag[T <: VNode] = TypedTag[T]

    override def makeAbstractTypedTag[T <: VNode](tag: String, void: Boolean, ns: generic.Namespace): ConcreteHtmlTag[T] =
      TypedTag(tag, List.empty, void, ns)

    protected[this] override implicit def stringAttrX: GenericAttr[String] = new GenericAttr[String]
    protected[this] override implicit def stringPixelStyleX: GenericPixelStyle[String] = new GenericPixelStyle[String](stringStyleX)
    protected[this] override implicit def stringStyleX: GenericStyle[String] = new GenericStyle[String]

    implicit def UnitFrag(u: Unit): StringFrag = StringFrag("")

    implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => Frag) extends Frag {
      def applyTo(vnode: VNode): Unit = xs.foreach(_.applyTo(vnode))

      def render: VNode = m.fragment(new js.Object, xs.map(_.render).toJSArray)
    }
  }

  case class StringFrag(v: String) extends Frag {
    def render: VNode = m.fragment(new js.Object, js.Array[VNode.Child](""))
  }

  class GenericAttr[T] extends AttrValue[T] {
    override def apply(t: VNode, a: Attr, v: T): Unit = {
      t.attrs += a.name -> v.toString
    }
  }

  class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T] {
    override def apply(s: Style, v: T) = generic.StylePair(s, v, ev)
  }

  class GenericStyle[T] extends StyleValue[T] {
    override def apply(t: VNode, s: Style, v: T): Unit = {
      val styles =
        t.attrs.getOrElseUpdate("style", js.Dictionary[js.Any]())
          .asInstanceOf[js.Dictionary[js.Any]]

      styles(s.cssName) = v.toString
    }
  }

  case class TypedTag[T <: VNode](
      tag: String,
      modifiers: List[Seq[Modifier]],
      void: Boolean = false,
      namespace: generic.Namespace) extends generic.TypedTag[VNode, T, VNode] with Frag { self =>

    protected[this] type Self = TypedTag[T]

    override def render: T = m(tag).asInstanceOf[T]

    override def apply(xs: Modifier*): Self = {
      this.copy(tag = tag, void = void, modifiers = xs:: modifiers)
    }

    override def applyTo(parent: VNode): Unit = self.applyTo(parent)
  }
}
