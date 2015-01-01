organization := "org.merizen"

name := "hipconf"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.3"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

val liftFamily = "2.6"

val liftVersion = liftFamily + "-RC2"
val h2Version = "1.3.176"
val logbackVersion = "1.1.2"
val liquibaseVersion = "3.3.1"
val snakeYamlVersion = "1.14"

val normalizeCssVersion = "3.0.1"
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

jetty(
  libs = Seq(("org.eclipse.jetty" % "jetty-runner" % jettyVersion % "container").intransitive)
)

jrebel.webLinks += (sourceDirectory in Compile).value / "webapp"

jrebelSettings

def forConfiguration(c: Configuration, deps: ModuleID*) =
  deps map (_ % c)

def liftModule(moduleName: String) =
  "net.liftmodules" %% (moduleName + "_" + liftFamily)

libraryDependencies ++= forConfiguration(Compile,
  "net.liftweb" %% "lift-webkit" % liftVersion,
  "net.liftweb" %% "lift-squeryl-record" % liftVersion,
  "com.h2database" % "h2" % h2Version,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.liquibase" % "liquibase-core" % liquibaseVersion,
  "org.yaml" % "snakeyaml" % snakeYamlVersion,
  "org.webjars" % "jquery" % jqueryVersion,
  "org.webjars" % "normalize.css" % normalizeCssVersion
)

libraryDependencies ++= forConfiguration(Test,
  "info.cukes" % "cucumber-core" % cucumberVersion,
  "info.cukes" % "cucumber-junit" % cucumberVersion,
  "info.cukes" %% "cucumber-scala" % cucumberVersion,
  "info.cukes" % "cucumber-pro" % cucumberProVersion,
  "junit" % "junit" % junitVersion,
  "com.novocode" % "junit-interface" % junitInterfaceVersion,
  "org.seleniumhq.selenium" % "selenium-java" % seleniumVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion,
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
  "org.eclipse.jetty" % "jetty-plus" % jettyVersion
)

libraryDependencies ++= forConfiguration(Provided,
  "javax.servlet" % "javax.servlet-api" % servletApiVersion
)

libraryDependencies ++= forConfiguration(Runtime,
  "org.codehaus.groovy" % "groovy" % groovyVersion
)

