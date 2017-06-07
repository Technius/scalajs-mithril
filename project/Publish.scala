import sbt._
import Keys._

object Publish {
  lazy val settings = Seq(
    publishMavenStyle := true,
    publishArtifact := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots") 
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    pomExtra := (
      <url>http://github.com/Technius/scalajs-mithril</url>
      <licenses>
        <license>
          <name>MIT License</name>
          <url>https://opensource.org/licenses/MIT</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:Technius/scalajs-mithril.git</url>
        <connection>scm:git:git@github.com:Technius/scalajs-mithril.git</connection>
      </scm>
      <developers>
        <developer>
          <id>technius</id>
          <name>Bryan Tan</name>
          <url>http://technius.co</url>
        </developer>
      </developers>)
    )
}
