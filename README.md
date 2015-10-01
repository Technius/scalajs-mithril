# Scala.js facades for Mithril.js

This is an experimental library that provides facades for [Mithril](https://lhorie.github.io/mithril/index.html).

## TODO
* `m.request`
* `m.trust`
* Other rendering functions
* Publish snapshots to Maven Central
* Fix issues/limitations (see relevant section below)
* ScalaDoc

## Usage

```scala
import co.technius.scalajs.mithril._

import org.scalajs.dom
import scala.scalajs.js

// Just define your component...
object MyComponent {

  // Use js.Undefined if you don't need a controller
  def controller() = new MyController

  // See the known issues/limitations
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
    // ...and then attach it to the DOM
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

## Known Issues/Limitations

* Using `Seq` for the `children` argument in `m(selector, children)` functions doesn't work. Instead, use `js.Array`.
* `m(selector, children)` functions don't accept varargs yet. Instead, use `js.Array`.

## License
This library is licensed under the MIT License. See LICENSE for more details.
