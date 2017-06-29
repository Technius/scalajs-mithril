package benchmarks

import co.technius.scalajs.mithril._
import japgolly.scalajs.benchmark._
import japgolly.scalajs.benchmark.gui._
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import org.scalajs.dom.document

object Benchmarks extends js.JSApp {
  val sampleData = ('a' to 'g').permutations.map(_.mkString)

  def vnodeMithril() =
    m("div", js.Array[VNode](
      m("div.header", js.Array[VNode](
        m("h1", "Sample Data")
      )),
      m("ul.data-list", sampleData.map { data =>
        m("li", data)
      }.toJSArray)
    ))

  def vnodeScalatagsExt() = {
    import VNodeScalatags.all._
    div(
      div(cls := "header")(
        h1("Sample Data")
      ),
      ul(cls := "data-list")(sampleData.map { data =>
        m("li", data)
      }.toSeq)
    ).render
  }

  val vnodeSuite = GuiSuite(
    Suite("Simple VNode construction")(
      Benchmark("scalajs-mithril only") {
        vnodeMithril()
      },
      Benchmark("with scalatags extension") {
        vnodeScalatagsExt()
      }
    )
  )

  val renderSuite = GuiSuite(
    Suite("Rendering")(
      Benchmark("scalajs-mithril only") {
        val parent = document.createElement("div")
        m.render(parent, vnodeMithril())
      },
      Benchmark("with scalatags extension") {
        val parent = document.createElement("div")
        m.render(parent, vnodeScalatagsExt())
      }
    )
  )

  def main(): Unit = {
    val body = document.getElementsByTagName("body")(0)
    BenchmarkGUI.renderMenu(body)(
      vnodeSuite,
      renderSuite
    )
  }
}
