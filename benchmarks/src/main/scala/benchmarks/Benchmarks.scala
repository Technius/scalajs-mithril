package benchmarks

import co.technius.scalajs.mithril._
import japgolly.scalajs.benchmark._
import japgolly.scalajs.benchmark.gui._
import scala.scalajs.js
import org.scalajs.dom.document

object Benchmarks extends js.JSApp {
  val suite = GuiSuite(
    Suite("Simple VNode construction")(
      Benchmark("mithril only") {
        m("div", js.Array[VNode](
          m("p", "Hi")
        ))
      },
      Benchmark("with scalatags") {
        import VNodeScalatags.all._
        div(
          p("Hi")
        ).render
      }
    )
  )

  def main(): Unit = {
    val body = document.getElementsByTagName("body")(0)
    BenchmarkGUI.renderSuite(body)(suite)
  }
}
