import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import org.scalajs.dom

import co.technius.scalajs.mithril._

object ExampleApp extends js.JSApp {
  def main(): Unit = {
    // Build routes based on demo choices
    val routes =
      DemoDropdown.choices map { case (_, link, compF) =>
        // We want to include the demo selection combobox above the displayed
        // demo, so a RouteResolver is used to customize how each route is
        // displayed.
        val view = RouteResolver.render { vnode =>

          // Create the component using the factory function
          val comp = compF()

          // Create the view
          m.fragment(new js.Object, js.Array[VNode](
            m(DemoDropdown.component),
            comp
          ))
        }

        // Map each route to the view
        link -> view
      }

    m.route(dom.document.getElementById("app"), "/", js.Dictionary(routes: _*))
  }
}

/**
  * Contains the combobox component that is used to select which demo to view by
  * changing the route.
  */
object DemoDropdown {

  /**
    * Component to display when no demo is selected
    */
  val defaultComponent = Component.viewOnly[js.Object] { _ =>
    m("p", "Select an example to display!")
  }

  /**
    * List of all of the demos shown in this app. They are organized by name,
    * link, and a factory function that produces the component.
    */
  val choices = Seq[(String, String, () => VNode)](
    ("None", "/", () => m(defaultComponent)),
    ("Hello", "/hello", () => m(HelloDemo.component)),
    ("Counter", "/counter", () => m(CounterDemo.component)),
    ("Tree", "/tree", () => m(TreeDemo.component, TreeDemo.Attrs("root"))),
    ("Data Fetch", "/data-fetch", () => m(DataFetchDemo.component)),
    ("Scalatags Demo", "/scalatags", () => m(ScalatagsDemo.component))
  )

  /**
    * The combobox component
    */
  val component = Component.viewOnly[js.Object] { vnode =>

    val currentRoute = m.route.get()

    // Build the list of demos
    val choiceList = choices map { case (name, link, _) =>
      val attrs = js.Dynamic.literal("value" -> name)
      if (currentRoute == link) {
        attrs.selected = true
      }
      m("option", attrs, name)
    }

    // Change route when a demo is selected
    val selectHandler: js.ThisFunction = (e: dom.raw.HTMLOptionElement) => {
      val linkOpt = choices.collectFirst { case (name, link, _) if name == e.value => link }
      m.route.set(linkOpt.getOrElse("/"))
    }

    val choiceBox = m("select", js.Dynamic.literal(
      "name" -> "examples",
      "onchange" -> selectHandler
    ), choiceList.toJSArray)

    choiceBox
  }
}
