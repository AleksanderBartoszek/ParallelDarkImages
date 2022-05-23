ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "ParallelDarkImages"
  )

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.6.19"
