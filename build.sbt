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
      ))
    )
  )
  .aggregate(core, examples)

lazy val core =
  Project("core", file("core"))
    .settings(Publish.settings: _*)
    .settings(
      name := """scalajs-mithril""",
      version := "0.2.0-SNAPSHOT",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.1",
        "org.scalatest" %% "scalatest" % "3.0.1" % "test"
      )
    )
    .enablePlugins(ScalaJSPlugin)

lazy val examples =
  Project("examples", file("examples"))
    .settings(
      name := """scalajs-mithril-examples""",
      version := "0.2.0-SNAPSHOT",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.1"
      ),
      npmDependencies in Compile += "mithril" -> "1.1.1"
    )
    .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
    .dependsOn(core)
