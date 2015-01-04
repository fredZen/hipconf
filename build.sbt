import Dependencies.d

val webappDir = settingKey[File]("The webapp directory for the container")

lazy val root = configure(project in file(".")
  , /* Project information */ _.settings(
    organization := "org.merizen"
    , name := "hipconf"
    , version := "0.1-SNAPSHOT"
  )
  , /* Scala compiler configuration */ _.settings(
    scalaVersion := "2.11.4"
    , scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
  , /* Library dependencies */ _.settings(
    libraryDependencies ++=
      forConfiguration(Compile
        , d.lift.webkit
        , d.lift.squerylRecord
        , d.h2
        , d.logbackClassic
        , d.liquibaseCore
        , d.snakeYaml
        , d.jquery
        , d.normalizeCss
      )
        ++ forConfiguration(Test
        , d.cucumber.core
        , d.cucumber.junit
        , d.cucumber.scala
        , d.cucumber.pro
        , d.junit
        , d.junitInterface
        , d.seleniumJava
        , d.scalatest
        , d.jetty.webapp
        , d.jetty.plus
      )
        ++ forConfiguration(Provided
        , d.servletApi
      )
        ++ forConfiguration(Runtime
        , d.groovy
      )
  )
  , /* Where to look for library dependencies */ _.settings(
    resolvers += Resolver.mavenLocal
  )
  , /* Shared definitions */ _.settings(
    webappDir := (sourceDirectory in Compile).value / "webapp"
  )
  , /* Container configuration (for container:start etc) */
  _.settings(jetty(libs = Seq((d.jetty.runner % "container").intransitive)): _*)
  , /* Jrebel configuration (needs path to jrebel.jar) */ if (!sys.env.contains("JREBEL_PATH")) identity else _.settings(
    jrebel.webLinks += webappDir.value
    , javaOptions in container ++= Seq(
      "-javaagent:" + sys.env("JREBEL_PATH")
      , "-noverify"
      , "-XX:+UseConcMarkSweepGC"
      , "-XX:+CMSClassUnloadingEnabled"
    )
  )
  .settings(jrebelSettings: _*)
)

def configure(p: Project, cs: (Project => Project)*) = cs.reduce(_ andThen _)(p)

def forConfiguration(c: Configuration, deps: ModuleID*) =
  deps map (_ % c)
