name := """scalajs-mithril"""

organization := "co.technius"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-Xlint",
  "-Xfatal-warnings"
)

enablePlugins(ScalaJSPlugin)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

