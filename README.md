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
addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.5.0")
```

Then, add the following lines to `build.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "co.technius" %%% "scalajs-mithril" % "0.2.0-SNAPSHOT"
enablePlugins(ScalaJSBundlerPlugin)

// Change mithril version to any version supported by this library
npmDependencies in Compile += "mithril" -> "1.0.1"
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
    val name = MithrilStream("Name")
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

This section assumes you are familiar with mithril. If you aren't, don't worry;
mithril can be picked up very quickly.

First, you'll need to define your component.
```scala
object MyComponent extends Component {
  type RootNode = GenericVNode[_, _]
  def view(vnode: RootNode): VNode = m("div", js.Array(
    m("p", "Hello world!")
    m("p", "How fantastic!")
  ))
}
```
In this example, `RootNode` is defined as a `GenericVNode[_,_]`, which is
parameterized on `State` and `Attrs`. `State` is the type that represents
`vnode.state`;`Attrs` represents `vnode.attr`.

To add some state to the component, create a `State` class and redefine
`RootNode` to include it as a parameter:
```scala
object MyComponent extends Component {
  type RootNode = GenericVNode[State, _]
  def view(vnode: RootNode): VNode = {
    import vnode.state
    m("div", js.Array(
      m("span", s"Hey there, ${state.name()}!"),
      m("input[type=text]", js.Dynamic.literal(
        oninput = m.withAttr("value", state.name),
        value = ctrl.name()
      ))
    ))
  }

  protected class State {
    val name = MithrilStream("Name")
  }
}
```

Due to the way mithril handles the fields in the component, runtime errors
occur if methods or functions are defined directly in the component from
Scala.js. One possible workaround is to define the functions in an inner object:
```scala
object MyComponent extends Component {
  type RootNode = GenericVNode[State, _]
  def view(vnode: RootNode) = {
    import helpers._
    myFunction(vnode.state)
    /* other code omitted */
  }
  
  protected class State { /* contents omitted */ }
  
  object helpers {
    def myFunction(state: State): Unit = {
      // do stuff
    }
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

By default, Mithril parses the response data into a `js.Object`. It may be
convenient to define a facade to hold the response data:

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

* Improve consistency of streams
* Improve vnode and component facades
* Improve `combine` and `merge` type signatures in MStream
* Improve documentation
* Fix issues/limitations (see relevant section above)
* ScalaDoc
* (In the not-so-far future) ScalaTags support

## License
This library is licensed under the MIT License. See LICENSE for more details.
