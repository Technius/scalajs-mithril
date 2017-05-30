package co.technius.scalajs.mithril

import co.technius.scalajs.mithril._
import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scalatags.{ Companion, DataConverters, generic, stylesheet }

import tagsext.raw

// Adapted from scalatags
object VNodeScalatags extends generic.Bundle[VNode, VNode, VNode]
    with generic.Aliases[VNode, VNode, VNode]
    with VNodeUtils {

  override object attrs extends Cap with tagsext.Attrs
  override object tags extends Cap with tagsext.Tags
  override object tags2 extends Cap with tagsext.Tags2
  override object styles extends Cap with Styles
  override object styles2 extends Cap with Styles2
  override object svgAttrs extends Cap with SvgAttrs
  override object svgTags extends Cap with tagsext.SvgTags

  override object implicits extends Aggregate
      with DataConverters
      with LowPriorityImplicits

  object all extends Cap
      with tagsext.Attrs
      with Styles
      with tagsext.Tags
      with DataConverters
      with Aggregate
      with LowPriorityImplicits

  object short extends Cap
      with tagsext.Tags
      with DataConverters
      with Aggregate
      with AbstractShort
      with LowPriorityImplicits {

    object * extends Cap with tagsext.Attrs with Styles
  }

  trait Aggregate extends generic.Aggregate[VNode, VNode, VNode] {

    implicit class ApplyTags(vnode: VNode) {
      def applyTags(mods: Modifier*) = mods.foreach(_.applyTo(vnode))
    }

    implicit def ClsModifier(s: stylesheet.Cls): Modifier = new Modifier {
      override def applyTo(n: VNode): Unit = {
        createAttrsIfNeeded(n)
        val attrs = n.attrs.asInstanceOf[js.Dictionary[js.Any]]
        val cx = attrs.get("class").map(_.toString + " ").getOrElse("") + s.toString
        attrs("class") = cx
      }
    }

    implicit class StyleFrag(s: generic.StylePair[VNode, _]) extends stylesheet.StyleSheetFrag {
      override def applyTo(st: stylesheet.StyleTree): stylesheet.StyleTree = {
        val dummyDiv = m("div")
        s.applyTo(dummyDiv)
        val styles = dummyDiv.attrs("style").asInstanceOf[js.Dictionary[js.Any]]
        val (name, value) = styles.head
        st.copy(styles = st.styles.updated(name, value.toString))
      }
    }

    override def genericAttr[T] = new VNodeScalatags.GenericAttr[T]
    override def genericStyle[T] = new VNodeScalatags.GenericStyle[T]
    override def genericPixelStyle[T](implicit ev: StyleValue[T]): VNodeScalatags.PixelStyleValue[T]  = new GenericPixelStyle[T](ev)
    override def genericPixelStylePx[T](implicit ev: StyleValue[String]): PixelStyleValue[T]  = new VNodeScalatags.GenericPixelStylePx[T](ev)

    override val RawFrag = VNodeScalatags.RawFrag
    override type RawFrag = VNodeScalatags.RawFrag

    override val StringFrag = VNodeScalatags.StringFrag
    override type StringFrag = VNodeScalatags.StringFrag

    override def raw(s: String) = RawFrag(s)

    override implicit def stringFrag(v: String) = VNodeScalatags.stringFrag(v)
  }

  implicit def stringFrag(v: String): StringFrag = StringFrag(v)

  trait Cap extends Util with tagsext.TagFactory { self =>

    override type ConcreteHtmlTag[T <: VNode] = TypedTag[T]

    override def makeAbstractTypedTag[T <: VNode](tag: String, void: Boolean, ns: generic.Namespace): ConcreteHtmlTag[T] =
      TypedTag(tag, List.empty, void, ns)

    protected[this] override implicit def stringAttrX: GenericAttr[String] = new GenericAttr[String]
    protected[this] override implicit def stringPixelStyleX: GenericPixelStyle[String] = new GenericPixelStyle[String](stringStyleX)
    protected[this] override implicit def stringStyleX: GenericStyle[String] = new GenericStyle[String]

    implicit def UnitFrag(u: Unit): StringFrag = StringFrag("")

    implicit class SeqFrag[A](xs: Seq[A])(implicit ev: A => Frag) extends tagsext.Frag {
      override def applyTo(vnode: VNode): Unit = xs.foreach(_.applyTo(vnode))

      def render: VNode = m.fragment(new js.Object, xs.map(_.render).toJSArray)
    }
  }

  object StringFrag extends Companion[StringFrag]
  case class StringFrag(v: String) extends tagsext.Frag {
    def render: VNode =
      new raw.RawVNode("#", js.undefined, new js.Object, v, js.undefined, js.undefined)
  }

  object RawFrag extends Companion[RawFrag]
  case class RawFrag(v: String) extends tagsext.Frag {
    def render: VNode = m.trust(v)
  }

  class GenericAttr[T] extends AttrValue[T] {
    override def apply(vnode: VNode, attr: Attr, value: T): Unit = {
      createAttrsIfNeeded(vnode)
      vnode.attrs += attr.name -> value.toString
    }
  }

  class GenericPixelStyle[T](ev: StyleValue[T]) extends PixelStyleValue[T] {
    override def apply(s: Style, v: T) = generic.StylePair(s, v, ev)
  }

  class GenericPixelStylePx[T](ev: StyleValue[String]) extends PixelStyleValue[T] {
    override def apply(s: Style, v: T) = generic.StylePair(s, v + "px", ev)
  }

  class GenericStyle[T] extends StyleValue[T] {
    override def apply(vnode: VNode, style: Style, value: T): Unit = {
      createAttrsIfNeeded(vnode)
      val styles =
        vnode.attrs.getOrElseUpdate("style", js.Dictionary[Any]())
          .asInstanceOf[js.Dictionary[Any]]

      styles(style.cssName) = value
    }
  }

  case class TypedTag[T <: VNode](
      tag: String,
      modifiers: List[Seq[Modifier]],
      void: Boolean = false,
      namespace: generic.Namespace) extends generic.TypedTag[VNode, T, VNode] with tagsext.Frag { self =>

    protected[this] type Self = TypedTag[T]

    override def render: T = {
      val n = m(tag, new js.Object)
      build(n)
      n.asInstanceOf[T]
    }

    override def apply(xs: Modifier*): Self = {
      this.copy(tag = tag, void = void, modifiers = xs:: modifiers)
    }
  }
}

trait LowPriorityImplicits extends VNodeUtils {
  implicit object bindJsAny extends generic.AttrValue[VNode, js.Any] {
    override def apply(vnode: VNode, attr: generic.Attr, value: js.Any): Unit = {
      createAttrsIfNeeded(vnode)
      vnode.attrs(attr.name) = value
    }
  }

  implicit def bindJsAnyLike[T](implicit ev: T => js.Any) = new generic.AttrValue[VNode, T] {
    override def apply(vnode: VNode, attr: generic.Attr, value: T): Unit = {
      createAttrsIfNeeded(vnode)
      vnode.attrs(attr.name) = value
    }
  }

  implicit class bindComponent[S,A](c: Component[S, A]) extends tagsext.Frag {
    override def render: VNode = m(c)
  }

  implicit class bindVNode(vnode: VNode) extends tagsext.Frag {
    override def render: VNode = vnode
  }
}

private[mithril] trait VNodeUtils {
  // TODO: ugly hack, needs to be refactored
  protected def createAttrsIfNeeded(vnode: VNode): Unit = {
    val rawNode = vnode.asInstanceOf[raw.RawVNode]
    if (rawNode.attrs == js.undefined) {
      rawNode.asInstanceOf[js.Dynamic].attrs = new js.Object
    }
  }
}
