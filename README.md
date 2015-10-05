# Scala.js facades for Mithril.js

This is an experimental library that provides facades for [Mithril](https://lhorie.github.io/mithril/index.html).

## TODO
* Complete `m.request` implementation
* `m.trust`
* `m.render`
* Publish snapshots to Maven Central
* Fix issues/limitations (see relevant section below)
* ScalaDoc

## Example

```scala
import co.technius.scalajs.mithril._

import org.scalajs.dom
import scala.scalajs.js

object MyComponent extends Component {

  def controller() = new MyController

  def view(ctrl: Controller) = js.Array(
    m("span", s"Hi, ${ctrl.name()}!"),
    m("input[type=text]", js.Dynamic.literal(
      oninput = m.withAttr("value", ctrl.name),
      value = ctrl.name()
    )
  )

  class MyController {
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

First, you'll need to define your component. In Mithril, a controller is
optional. For a component without a controller, create an object that
subclasses `ViewComponent` and implement the `view` method.

```scala
@ScalaJSDefined
object MyComponent extends ViewComponent {
  def view() = js.Array(
    m("p", "Hello world!")
    m("p", "How fantastic!")
  )
}
```

A component with a controller is a bit more complicated. You'll need to create
an object that subclasses `Component`, define the `Controller` type, and
implement both the `view` and `controller` methods.

```scala
@ScalaJSDefined
class MyComponent extends Component {
  type Controller = MyController
  def controller() = new MyController
  def view(ctrl: Controller) = js.Array(
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

## Known Issues/Limitations

* Using `Seq` for the `children` argument in `m(selector, children)` functions doesn't work. Instead, use `js.Array`.
* `m(selector, children)` functions don't accept varargs yet. Instead, use `js.Array`.

## License
This library is licensed under the MIT License. See LICENSE for more details.
