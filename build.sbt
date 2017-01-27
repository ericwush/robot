name := "robot"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.12" % "3.0.1",
  "org.scalacheck" % "scalacheck_2.12" % "1.13.4")

lazy val commonSettings = Seq(
  version := "1.0",
  scalaVersion := "2.12.1",
  test in assembly := {}
)

lazy val app = (project in file("scala")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("app.App")
  )