import Dependencies.d
import PropertiesHelper._

val webappDir = settingKey[File]("The webapp directory for the container")

val commonConfig: Project=>Project = configure (_) (
  /* Project information */ _.settings(
    organization := "org.merizen"
    , name := "hipconf"
    , version := "0.1-SNAPSHOT"
  )
  , /* Scala compiler configuration */ _.settings(
    scalaVersion := "2.11.4"
    , scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
  , /* Where to look for library dependencies */ _.settings(
    resolvers += Resolver.mavenLocal
  )
)

lazy val root = configure(project in file("."))(
  commonConfig
  , /* Project information */ _.settings(
    name := "hipconf"
  )
  , /* Library dependencies */ _.settings(
    libraryDependencies ++=
      forConfiguration(Compile
        , d.lift.webkit
        , d.lift.squerylRecord
        , d.h2
        , d.liquibaseCore
        , d.snakeYaml
        , d.logbackClassic
        , d.commonsIo
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
  , /* Shared definitions */ _.settings(
    webappDir := (sourceDirectory in Compile).value / "webapp"
  )
  , /* Shared test helpers */ _.dependsOn(
    testHelpers % "test"
  ).aggregate(
    testHelpers
  ).settings(
  unmanagedResourceDirectories in Test += baseDirectory.value / "features"
)
  , /* Container configuration (for container:start etc) */
  _.settings(jetty(libs = Seq((d.jetty.runner % "container").intransitive)): _*)
  , /* Make location of webapp dir available to selenium tests */ _.settings(
    resourceGenerators in Test += Def.task {
      val file = (resourceManaged in Test).value / "webapp.properties"
      writeProperties(file, Map("webappDir" -> webappDir.value.toString))
      Seq(file)
    }.taskValue
  )
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

lazy val testHelpers = configure(project)(
  commonConfig
  , /* Project information */ _.settings(
    name := "hipconf-test-helpers"
  )
  , /* Library dependencies */ _.settings(
    libraryDependencies ++=
      forConfiguration(Compile
        , d.cucumber.core
        , d.cucumber.scala
        , d.junit
      )
  )
)

def configure(p: Project)(cs: (Project => Project)*) = cs.reduce(_ andThen _)(p)

def forConfiguration(c: Configuration, deps: ModuleID*) =
  deps map (_ % c)
