crossScalaVersions in ThisBuild := Seq("2.12.0", "2.11.8")
scalaVersion in ThisBuild := crossScalaVersions.value.head

lazy val sharedSettings = Seq(
  version := "0.2.0-SNAPSHOT",
  organization := "co.technius",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-unchecked",
    "-Yno-adapted-args",
    "-Xlint",
    "-Xfatal-warnings"
  )
)

lazy val root = (project in file(".")).aggregate(core, examples)

lazy val core =
  Project("core", file("core"))
    .settings(sharedSettings: _*)
    .settings(Publish.settings: _*)
    .settings(
      name := """scalajs-mithril""",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.1",
        "org.scalatest" %% "scalatest" % "3.0.0" % "test"
      )
    )
    .enablePlugins(ScalaJSPlugin)

lazy val examples =
  Project("examples", file("examples"))
    .settings(sharedSettings: _*)
    .settings(
      name := """scalajs-mithril-examples""",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.1"
      ),
      npmDependencies in Compile += "mithril" -> "lhorie/mithril.js#rewrite"
    )
    .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
    .dependsOn(core)
