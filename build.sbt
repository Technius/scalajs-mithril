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
      ),
      scalacOptions ++= {
        if (scalaJSVersion.startsWith("0.6."))
          Seq("-P:scalajs:sjsDefinedByDefault")
        else
          Nil
      },
      publishArtifact := false
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
        "org.scala-js" %%% "scalajs-dom" % Versions.scalaJsDom,
        "org.scalatest" %%% "scalatest" % Versions.scalatest % "test"
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
        "com.lihaoyi" %%% "scalatags" % Versions.scalatags
      )
    )
    .enablePlugins(ScalaJSPlugin)
    .dependsOn(core)

lazy val tests =
  Project("tests", file("tests"))
    .settings(
      name := "tests",
      libraryDependencies += "org.scalatest" %%% "scalatest" % Versions.scalatest % "test",
      npmDependencies in Compile += "mithril" -> mithrilVersion,
      requiresDOM in Test := true
    )
    .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
    .dependsOn(core, scalatagsExt)


lazy val examples =
  Project("examples", file("examples"))
    .settings(
      name := """scalajs-mithril-examples""",
      version := "0.2.0-SNAPSHOT",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % Versions.scalaJsDom
      ),
      npmDependencies in Compile += "mithril" -> mithrilVersion,
      scalaJSUseMainModuleInitializer := true
    )
    .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
    .dependsOn(core, scalatagsExt)

lazy val benchmarks =
  Project("benchmarks", file("benchmarks"))
    .settings(
      name := """benchmarks""",
      libraryDependencies ++= Seq(
        "com.github.japgolly.scalajs-benchmark" %%% "benchmark" % "0.2.4",
        "org.scala-js" %%% "scalajs-dom" % Versions.scalaJsDom
      ),
      npmDependencies in Compile ++= Seq(
        "react" -> "15.5.4",     // needed by scalajs-benchmark
        "react-dom" -> "15.5.4", // needed by scalajs-benchmark
        "chart.js" -> "1.0.2",   // needed by scalajs-benchmark
        "mithril" -> mithrilVersion
      ),
      npmDevDependencies in Compile ++= Seq(
        "expose-loader" -> "0.7.1" // expose scalajs-benchmark dependencies to global
      ),
      webpackConfigFile := Some(baseDirectory.value / "webpack.config.js")
    )
    .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
    .dependsOn(core, scalatagsExt)
