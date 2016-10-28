lazy val sharedSettings = Seq(
  version := "0.1.0",
  organization := "co.technius",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
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
        "org.scala-js" %%% "scalajs-dom" % "0.9.0",
        "org.scalatest" %% "scalatest" % "2.2.4" % "test"
      )
    )
    .enablePlugins(ScalaJSPlugin)

lazy val examples =
  Project("examples", file("examples"))
    .settings(sharedSettings: _*)
    .settings(
      name := """scalajs-mithril-examples""",
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.0"
      ),
      jsDependencies += "org.webjars" % "mithril" % "0.2.5" / "mithril.js"
    )
    .enablePlugins(ScalaJSPlugin)
    .dependsOn(core)
