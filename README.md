# Scala.js facades for Mithril.js

[![Build Status](https://travis-ci.org/Technius/scalajs-mithril.svg?branch=master)](https://travis-ci.org/Technius/scalajs-mithril)

This is an experimental library that provides facades
for [Mithril](https://lhorie.github.io/mithril/index.html).

At the moment, scalajs-mithril is being rewritten to support mithril `1.1.1`.
The `0.1.0` version of scalajs-mithril for mithril `0.2.5` can be
found [here](/tree/v0.1.0).

Mithril 1.x.y is significantly different from 0.2.0, which is why this rewrite
is required.

## Table of Contents

* [Setup](#setup)
* [Example](#example)
* [The Basics](#the-basics)
  * [Using the Helpers](#using-the-helpers)
  * [Subclassing Component](#subclassing-component)
* [Routing](#routing)
* [Making Web Requests](#making-web-requests)
* [Scalatags Support](#scalatags-support)
* [Compiling](#compiling)
* [License](#license)

## Setup

Add `scalajs-bundler` to `project/plugins.sbt`:
```scala
addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.6.0")
```

Then, add the following lines to `build.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "co.technius" %%% "scalajs-mithril" % "0.2.0-SNAPSHOT"
enablePlugins(ScalaJSBundlerPlugin)

// Change mithril version to any version supported by this library
npmDependencies in Compile += "mithril" -> "1.1.1"
```

Build your project with `fastOptJS::webpack`.

## Example

```scala
import co.technius.scalajs.mithril._
import scala.scalajs.js
import org.scalajs.dom

object MyModule {
  val component = Component.stateful[State, js.Object](_ => new State) { vnode =>
    import vnode.state
    m("div", js.Array[VNode](
      m("span", s"Hi, ${state.name()}!"),
      m("input[type=text]", js.Dynamic.literal(
        oninput = m.withAttr("value", state.name),
        value = state.name()
      ))
    ))
  }

  class State {
    val name = MithrilStream("Name")
  }
}

object MyApp extends js.JSApp {
  def main(): Unit = {
    m.mount(dom.document.getElementById("app"), MyModule.component)
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

See the [examples folder](/examples/src/main/scala)
for complete examples.

## The Basics

This section assumes you are familiar with mithril. If you aren't, don't worry;
mithril can be picked up very quickly.

A component is parametized on `State` (for `vnode.state`) and `Attrs` (for
`vnode.attrs`). If `State` and `Attrs` are not neccessary for the component, use
`js.Object` and `js.Dictionary[js.Any]` should be used instead, respectively.

There are two ways to define a component using this library:

1. Use one of the component helpers, such as `Component.stateful` or
   `Component.viewOnly`.
2. Subclass `Component`.

While the helpers provide limited control over components, they are sufficiently
powerful for most situations. If more control over a component is desired (e.g.
overriding lifecycle methods), then subclass `Component` instead.

Virtual DOM nodes (vnodes) are defined as `GenericVNode[State, Attr]`. For
convenience, a type alias `VNode` is defined as `GenericVNode[js.Object,
js.Dictionary[js.Any]]` to reduce the hassle of adding type signatures for
vnodes.

### Using the Helpers

Defining a stateless component is very simple using the `Component.viewOnly` function:

```scala
import co.technius.scalajs.mithril._
import scala.scalajs.js

object HelloApp extends js.JSApp {
  // viewOnly has the view function as an argument
  val component = Component.viewOnly[js.Object] { vnode =>
    m("div", "Hello world!")
  }
  
  def main(): Unit = {
    m.mount(dom.document.getElementById("app"), component)
  }
}
```

`viewOnly` is parameterized on `Attr`, so it's possible to handle arguments:

```scala
// First, create a class to represent the attriute object
case class Attr(name: String)

// Then, supply it as a type parameter to viewOnly
val component = Component.viewOnly[Attr] { vnode =>
  m("div", "Hello " + vnode.attr.name)
}
```

It's more common to see stateful components, though. They can be defined using
`Component.stateful`.

```scala
import co.technius.scalajs.mithril._
import scala.scalajs.js

object NameApp extends js.JSApp {

  // Just like for attributes, define a state class to hold the state
  protected class State {
    var name = "Name"
  }

  // stateful has two arguments:
  // - A function to create the state from a vnode
  // - The view function
  // It's also parameterized on state and attributes
  val component = Component.stateful[State, js.Object](_ => new State) { vnode =>
    import vnode.state
    m("div", js.Array[VNode](
      m("span", s"Hi, ${state.name}!"),
      m("input[type=text]", js.Dynamic.literal(
        oninput = m.withAttr("value", newName => state.name = newName),
        value = state.name
      ))
    ))
  }
  
  def main(): Unit = {
    m.mount(dom.document.getElementById("app"), component)
  }
}
```

### Subclassing Component

Subclassing component requires more boilerplate, but it gives more fine-grained
control over the component's lifecycle.

First, you'll need to define your component, which is parametized on `State`
(for `vnode.state`) and `Attrs` (for `vnode.attrs`). If `State` and `Attrs` are
not neccessary for the component, use `js.Object`.

```scala
object MyComponent extends Component[js.Object, js.Object] {
  // RootNode is defined as an alias
  override val view = (vnode: RootNode) => {
    m("div", js.Array(
      m("p", "Hello world!")
      m("p", "How fantastic!")
    ))
  }
}
```

`Component` is a subtrait of `Lifecycle`, which defines the lifecycle methods.
Thus, it's possible to override the lifecycle methods in a `Component`. Here's
an example of a stateful component that overrides `oninit` to set the state:

```scala
object MyComponent extends Component[MyComponentState, js.Object] {
  override val oninit = js.defined { (vnode: RootNode) =>
    vnode.state = new MyComponentState
  }

  override val view = { vnode: RootNode =>
    import vnode.state
    m("div", js.Array(
      m("span", s"Hey there, ${state.name()}!"),
      m("input[type=text]", js.Dynamic.literal(
        oninput = m.withAttr("value", state.name),
        value = ctrl.name()
      ))
    ))
  }
}

class MyComponentState {
  val name = MithrilStream("Name")
}
```

Due to the way mithril handles the fields in the component, runtime errors
occur if methods or functions are defined directly in the component from
Scala.js. One possible workaround is to define the functions in an inner object:

```scala
object MyComponent extends Component[MyComponentState, js.Object] {
  override val oninit = js.defined { (vnode: RootNode) =>
    vnode.state = new MyComponentState
  }

  override val view = { (vnode: RootNode) =>
    import helpers._
    myFunction(vnode.state)
    /* other code omitted */
  }
  
  object helpers {
    def myFunction(state: MyComponentState): Unit = {
      // do stuff
    }
  }
}

class MyComponentState { /* contents omitted */ }
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

To use `Attrs` in a component, define a class for `Attrs` and change the
parameter on `Component`. The component should then be created by calling
`m(component, attrs)` (see the TreeComponent example).

```scala
import co.technius.scalajs.mithril._

case class MyAttrs(name: String)

object MyComponent extends Component[js.Object, MyAttrs] {
  def view(vnode: RootNode) = m("span", vnode.attrs.name)
}
```

## Routing

To use Mithril's routing functionality, use `m.route` as it is defined in mithril:

```scala
import co.technius.scalajs.mithril._
import org.scalajs.dom
import scala.scalajs.js

object MyApp extends js.JSApp {
  val homeComponent = Component.viewOnly[js.Object] { vnode =>
    m("div", "This is the home page")
  }

  val pageComponent = Component.viewOnly[js.Object] { vnode =>
    m("div", "This is another page")
  }

  def main(): Unit = {
    val routes = js.Dictionary[MithrilRoute.Route](
      "/" -> homeComponent,
      "/page" -> pageComponent
    )
    m.route(dom.document.getElementById("app"), "/", routes)
  }
}
```

For convenience, there is an alias that constructs the routes dictionary
`m.route` automatically:

```scala
m.route(dom.document.getElementById("app"), "/", routes)(
  "/" -> homeComponent,
  "/page" -> pageComponent
)
```

Instead of using a component for a route,
a [`RouteResolver`](https://mithril.js.org/route.html#routeresolver) may be used
instead. There are two ways to construct a `RouteResolver`: using a helper
method or subclassing `RouteResolver`.

`RouteResolver.render` creates a `RouteResolver` that contains a `render`
function. The helper accepts the `render` function as a parameter:

```scala
m.route(dom.document.getElementById("app"), "/", routes)(
  "/" -> RouteResolver.render { vnode =>
    m("div", js.Array[VNode](
      m("h1", "Home component"),
      homeComponent
    ))
  },
  "/page" -> pageComponent
)
```

`RouteResolver.onmatch` creates a `RouteResolver` that contains an `onmatch`
function. The helper accepts the `onmatch` function as a parameter:

```scala
val accessDeniedComponent = Component.viewOnly[js.Object] { vnode =>
  m("div", "Incorrect or missing password!")
}

val secretComponent = Component.viewOnly[js.Object] { vnode =>
  m("div", "Welcome to the secret page!")
}

m.route(dom.document.getElementById("app"), "/", routes)(
  "/secret" -> RouteResolver.onmatch { (params, requestedPath) =>
    // check if password is correct
    if (params.get("password").fold(false)(_ == "12345")) {
      secretComponent
    } else {
      accessDeniedComponent
    }
  }
)
```

If it is required to define both `render` and `onmatch`, subclass
`RouteResolver`. Note that this library always ensures that `render` is defined,
but allows `onmatch` to be undefined.

```scala
val helloComponent = Component.viewOnly[js.Object](_ => m("div", "Hello world!"))

val myRouteResolver = new RouteResolver {
  override def onmatch = js.defined { (params, requestedPath) =>
    helloComponent
  }

  override def render = { vnode =>
    vnode
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

Then, pass the options to `m.request`, which will return a `js.Promise[T]`:
```scala
val reqPromise = m.request(opts)

// convert Promise[T] to Future[T]
// use of Future requires implicit ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
reqPromise.toFuture.foreach { data =>
  println(data)
}
```

By default, Mithril parses the response data into a `js.Object`. It may be
convenient to define a facade to hold the response data:

```scala
// Based on examples/src/main/resources/sample-data.json
import scala.concurrent.ExecutionContext.Implicits.global
@js.native
trait MyData extends js.Object {
  val key: String
  val some_number: Int
}

val opts = new XHROptions[MyData](method = "GET", url = "/path/to/request")

m.request(opts).toFuture foreach { data =>
  println(data.key)
  println(data.some_number)
}
```

## Scalatags Support

There is also support for [Scalatags](http://lihaoyi.com/scalatags), which can
make it easier to create views. Add the following line to `build.sbt`:

```scala
libraryDependencies += "co.technius" %%% "scalajs-mithril" % "0.2.0-SNAPSHOT"
```

Then, import `co.technius.scalajs.mithril.VNodeScalatags.all._`. If you already
imported the `mithril` package as a wildcard, simply import
`VNodeScalatags.all._`. You can then use tags in your components:

```scala
val component = Component.viewOnly[js.Object] { vnode =>
  div(id := "my-div")(
    p("Hello world!")
  ).render
}
```

It's also possible to use `VNode`s with scalatags:

```scala
// Components are also VNodes
val embeddedComponent = Component.viewOnly[js.Object] { vnode =>
  p("My embedded component").render
}

val component = Component.viewOnly[js.Object] { vnode =>
  div(
    m("p", "My root component"),
    embeddedComponent
  ).render
}
```

The lifecycle methods, as well as `key`, are usable as attributes in scalatags:

```scala
class Attrs(items: scala.collection.mutable.Map[String, String])

val component = Component.viewOnly[Attrs] { vnode =>
  ul(oninit := { () => println("Initialized!") })(
    vnode.attrs.items.map {
      case (id, name) => li(key := id, name)
    }
  ).render
}
```

See the [scalatags demo](/examples/src/main/scala/ScalatagsDemo.scala) for an
example.

## Compiling

Compile the core project with `core/compile`. Examples can be built locally by
running `examples/fastOptJS::webpack` and then navigating to
`examples/src/main/resources/index.html`. To run tests, use `tests/test`.

## TODO

* Add missing functions from Mithril 1.1.1
* Create documentation
* ScalaDoc
* Write more tests
* Add a component builder
* GitHub Pages

## License
This library is licensed under the MIT License. See LICENSE for more details.
