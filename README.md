# Scala.js facades for Mithril.js

This is an experimental library that provides facades for [Mithril](https://lhorie.github.io/mithril/index.html).

## TODO

* Use macros to type-check view and controller methods
* Complete `m.request` implementation
* `m.trust`
* `m.render`
* Publish snapshots to Bintray or Maven Central
* Fix issues/limitations (see relevant section below)
* ScalaDoc

## Example

```scala
import co.technius.scalajs.mithril._

import org.scalajs.dom
import scala.scalajs.js

object MyComponent extends Component {

  override val controller: js.Function = () => new Controller

  val view: js.Function = (ctrl: Controller) => js.Array(
    m("span", s"Hi, ${ctrl.name()}!"),
    m("input[type=text]", js.Dynamic.literal(
      oninput = m.withAttr("value", ctrl.name),
      value = ctrl.name()
    )
  )

  class Controller {
    val name = m.prop("Name")
  }
}

object MyApp extends js.JSApp {
  def main(): Unit = {
    m.mount(dom.document.getElementById("app"), MyComponent)
  }
}
```

```html
<!DOCTYPE HTML>
<html>
  <head>
    <title>scalajs-mithril Example</title>
  </head>
  <body>
    <div id="app"></div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/mithril/0.2.0/mithril.js"></script>
    <script src="scalajs-mithril-fastopt.js"></script>
    <script>MyApp().main();</script>
  </body>
</html>
```

## The Basics

First, you'll need to define your component. To do this, create an object,
subclass `Component` and implement the view functon.

```scala
object MyComponent extends Component {
  val view: js.Function = () => js.Array(
    m("p", "Hello world!")
    m("p", "How fantastic!")
  )
}
```

In Mithril, a controller is optional. If you want to use a controller, create a
class for your controller, override the `controller` function, and add the
controller as an argument to your view function.

```scala
class MyComponent extends Component {
  override val controller: js.Function = () => new MyController
  val view: js.Function = (ctrl: Controller) => js.Array(
    m("span", s"Hey there, ${ctrl.name()}!"),
    m("input[type=text]", js.Dynamic.literal(
      oninput = m.withAttr("value", ctrl.name),
      value = ctrl.name()
    ))
  )

  class MyController {
    val name = m.prop("Name")
  }
}
```

Lastly, call `m.mount` with your controller:

```scala
import co.technius.scalajs.mithril._
import org.scalajs.dom
import scala.scalajs.js

object MyApp extends js.JSApp {
  def main(): Unit = {
    m.mount(dom.document.getElementById("app"), MyComponent)
  }
}
```

## Known Issues/Limitations

* Type safety isn't that good for components right now.
* Using `Seq` for the `children` argument in `m(selector, children)` functions doesn't work. Instead, use `js.Array`.
* `m(selector, children)` functions don't accept varargs yet. Instead, use `js.Array`.

## License
This library is licensed under the MIT License. See LICENSE for more details.
