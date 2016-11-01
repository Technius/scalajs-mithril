# Scala.js facades for Mithril.js

This is an experimental library that provides facades for [Mithril](https://lhorie.github.io/mithril/index.html).

You are currently viewing the `0.2.0` branch for the experimental `1.0.0` version
of mithril. The stable `0.1.0` version of scalajs-mithril for mithril `0.2.5`
can be found [here](/tree/v0.1.0).

Mithril 1.0.0 is significantly different from 0.2.0, which is why this rewrite
is required.

## Setup
Add `scalajs-bundler` to `project/plugins.sbt`:
```scala
addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.1")
```

Then, add the following lines to `build.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "co.technius" %%% "scalajs-mithril" % "0.2.0-SNAPSHOT"
```

Build your project with `fastOptJS::webpack`.

## Example

```scala
import co.technius.scalajs.mithril._
import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined
import org.scalajs.dom

@ScalaJSDefined
object MyComponent extends Component {

  type RootNode = GenericVNode[State, _]

  def oninit(vnode: RootNode) = {
    vnode.state = new State
  }

  def view(vnode: RootNode): VNode = {
    import vnode.state
    m("div", js.Array[VNode](
      m("span", s"Hi, ${state.name()}!"),
      m("input[type=text]", js.Dynamic.literal(
        oninput = m.withAttr("value", state.name),
        value = state.name()
      ))
    ))
  }

  protected class State {
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
    <script src="example-fastopt-bundle.js"></script>
  </body>
</html>
```

See the [examples folder](https://github.com/Technius/scalajs-mithril/tree/master/examples/src/main/scala)
for complete examples.

## The Basics

First, you'll need to define your component. To do this, create an object,
subclass `Component` and implement the view function.

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

## Making Web Requests

First, create an `XHROptions[T]`, where `T` is the data to be returned:

```scala
val opts = new XHROptions[js.Object](method = "GET", url = "/path/to/request")
```

It's possible to use most of the optional arguments:
```scala
val opts =
  new XHROptions[js.Object](
    method = "POST",
    url = "/path/to/request",
    data = js.Dynamic.literal("foo" -> 1, "bar" -> 2),
    background = true)
```

Then, pass the options to `m.request`, which will return a `Promise[T]`:
```scala
m.request(opts).foreach { data =>
  println(data)
}
```

`T` is restricted to subtypes of `js.Any`, so Scala types can't be readily used
with `m.request`. Of course, then response type could be parsed manually, but it
may be more convenient to define a facade to hold the response data:

```scala
// Based on examples/src/main/resources/sample-data.json
@js.native
trait MyData extends js.Object {
  val key: String
  val some_number: Int
}

val opts = new XHROptions[MyData](method = "GET", url = "/path/to/request")

m.request(opts).foreach { data =>
  println(data.key)
  println(data.some_number)
}
```

## Known Issues/Limitations

* Type safety isn't that good for components right now.
* Using `Seq` for the `children` argument in `m(selector, children)` functions doesn't work. Instead, use `js.Array`.
* `m(selector, children)` functions don't accept varargs yet. Instead, use `js.Array`.

## TODO

* Define facades for mithril streams
* Rewrite route facade
* Improve documentation
* Fix issues/limitations (see relevant section above)
* ScalaDoc
* (In the far future) ScalaTags support

## License
This library is licensed under the MIT License. See LICENSE for more details.
