name := "mention-enricher"

organization := "ml.generall"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"


libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"


libraryDependencies += "ml.generall" %% "scala-common" % "1.0-SNAPSHOT"

libraryDependencies += ("ml.generall" %% "ner-scala" % "1.0-SNAPSHOT").exclude("commons-logging", "commons-logging")

libraryDependencies += "ml.generall" %% "elastic-scala" % "1.0-SNAPSHOT"


libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.5.42"

libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"

resolvers += Resolver.mavenLocal

dependencyOverrides += "org.scalatest" %% "scalatest" % "3.0.0" % "test"


mainClass in Compile := Some("ml.generall.ConvertApp")
mainClass in assembly := Some("ml.generall.ConvertApp")

test in assembly := {}

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last == "groovy-release-info.properties" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last == "test.sc" => MergeStrategy.discard
  case PathList("com", "trueaccord", xs @ _*) => MergeStrategy.first
  case PathList("com", "google", xs @ _*) => MergeStrategy.first
  case PathList("org", "slf4j", xs @ _*) => MergeStrategy.first
  case PathList("commons-logging", _, xs @ _*) => MergeStrategy.first
  case PathList("org", "joda", "time", "base", "BaseDateTime.class") => MergeStrategy.first
  case PathList("commons-logging", "commons-logging-api", xs @ _*) => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}


// If you need to specify main classes manually, use packSettings and packMain
packSettings

// [Optional] Creating `hello` command that calls org.mydomain.Hello#main(Array[String])
packMain := Map("converter" -> "ml.generall.ConvertApp")