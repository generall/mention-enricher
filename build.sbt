name := "mention-enricher"

organization := "ml.generall"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"


libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"


libraryDependencies += "ml.generall" %% "scala-common" % "1.0-SNAPSHOT"

libraryDependencies += "ml.generall" %% "ner-scala" % "1.0-SNAPSHOT"


libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.5.42"

resolvers += Resolver.mavenLocal
