val mithrilVersion = "1.1.1"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(Seq(
      organization := "co.technius",
      crossScalaVersions := Seq("2.12.1", "2.11.8"),
      scalaVersion := crossScalaVersions.value.head,
      scalacOptions ++= Seq(
        "-feature",
        "-deprecation",
        "-unchecked",
        "-Yno-adapted-args",
        "-Xlint",
        "-Xfatal-warnings"
      )
    ))
  )
  .aggregate(core, scalatagsExt, examples)

lazy val core =
  Project("core", file("core"))
    .settings(Publish.settings: _*)
    .settings(
      name := """scalajs-mithril""",
      version := "0.2.0-SNAPSHOT",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.1",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test"
      )
    )
    .enablePlugins(ScalaJSPlugin)

lazy val scalatagsExt =
  Project("scalatags-ext", file("scalatags-ext"))
    .settings(Publish.settings: _*)
    .settings(
      name := """scalajs-mithril-scalatags""",
      version := "0.2.0-SNAPSHOT",
      libraryDependencies ++= Seq(
        "com.lihaoyi" %%% "scalatags" % "0.6.5"
      )
    )
    .enablePlugins(ScalaJSPlugin)
    .dependsOn(core)

lazy val tests =
  Project("tests", file("tests"))
    .settings(
      name := "tests",
      libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.1" % "test",
      npmDependencies in Compile += "mithril" -> mithrilVersion,
      requiresDOM in Test := true,
      version in installJsdom := "9.12.0" // hack until bundler updates to sjs 0.6.16
    )
    .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
    .dependsOn(core, scalatagsExt)


lazy val examples =
  Project("examples", file("examples"))
    .settings(
      name := """scalajs-mithril-examples""",
      version := "0.2.0-SNAPSHOT",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.1"
      ),
      npmDependencies in Compile += "mithril" -> mithrilVersion
    )
    .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
    .dependsOn(core)
