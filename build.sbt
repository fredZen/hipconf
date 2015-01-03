val liftVersion = "2.6-RC2"
val h2Version = "1.3.176"
val logbackVersion = "1.1.2"
val liquibaseVersion = "3.3.1"
val snakeYamlVersion = "1.14"

val normalizeCssVersion = "3.0.2"
val jqueryVersion = "1.11.2"

val cucumberVersion = "1.2.0"
val junitVersion = "4.12"
val junitInterfaceVersion = "0.11"
val seleniumVersion = "2.44.0"
val scalatestVersion = "2.2.1"
val cucumberProVersion = "1.0.16"

val jettyVersion = "9.2.6.v20141205"
val servletApiVersion = "3.0.1"

val groovyVersion = "2.3.9"

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
        , "net.liftweb" %% "lift-webkit" % liftVersion
        , "net.liftweb" %% "lift-squeryl-record" % liftVersion
        , "com.h2database" % "h2" % h2Version
        , "ch.qos.logback" % "logback-classic" % logbackVersion
        , "org.liquibase" % "liquibase-core" % liquibaseVersion
        , "org.yaml" % "snakeyaml" % snakeYamlVersion
        , "org.webjars" % "jquery" % jqueryVersion
        , "org.webjars" % "normalize.css" % normalizeCssVersion
      )
        ++ forConfiguration(Test
        , "info.cukes" % "cucumber-core" % cucumberVersion
        , "info.cukes" % "cucumber-junit" % cucumberVersion
        , "info.cukes" %% "cucumber-scala" % cucumberVersion
        , "info.cukes" % "cucumber-pro" % cucumberProVersion
        , "junit" % "junit" % junitVersion
        , "com.novocode" % "junit-interface" % junitInterfaceVersion
        , "org.seleniumhq.selenium" % "selenium-java" % seleniumVersion
        , "org.scalatest" %% "scalatest" % scalatestVersion
        , "org.eclipse.jetty" % "jetty-webapp" % jettyVersion
        , "org.eclipse.jetty" % "jetty-plus" % jettyVersion
      )
        ++ forConfiguration(Provided
        , "javax.servlet" % "javax.servlet-api" % servletApiVersion
      )
        ++ forConfiguration(Runtime
        , "org.codehaus.groovy" % "groovy" % groovyVersion
      )
  )
  , /* Where to look for library dependencies */ _.settings(
    resolvers += Resolver.mavenLocal
  )
  , /* Shared definitions */ _.settings(
    webappDir := (sourceDirectory in Compile).value / "webapp"
  )
  , /* Container configuration (for container:start etc) */
  _.settings(jetty(libs = Seq(("org.eclipse.jetty" % "jetty-runner" % jettyVersion % "container").intransitive)): _*)
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
