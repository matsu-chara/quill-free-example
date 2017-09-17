name := "quill-free-example"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.38",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "io.getquill" %% "quill-jdbc" % "1.4.1-SNAPSHOT"
)
