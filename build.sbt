organization := "org.merizen"

name := "hipconf"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.3"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

val liftFamily = "2.6"
val liftVersion = liftFamily + "-RC1"
val h2Version = "1.3.176"
val logbackVersion = "1.1.2"
val liquibaseVersion = "3.2.2"
val snakeYamlVersion = "1.14"

val normalizeCssVersion = "3.0.1"
val jqueryVersion = "1.11.1"

val junitVersion = "4.11"
val seleniumVersion = "2.42.2"
val cucumberVersion = "1.2.0"
val junitInterfaceVersion = "0.11"
val scalatestVersion = "2.2.1"
val cucumberProVersion = "1.0.16"

val jettyVersion = "9.1.0.v20131115"
val servletApiVersion = "3.0.1"

webSettings

jrebel.webLinks <++= webappResources in Compile

jrebelSettings

def scope(scopeName: String, deps: ModuleID*) =
  deps map (_ % scopeName)

def liftModule(moduleName: String) =
  "net.liftmodules" %% (moduleName + "_" + liftFamily)

libraryDependencies ++= scope("compile",
  "net.liftweb" %% "lift-webkit" % liftVersion,
  "net.liftweb" %% "lift-squeryl-record" % liftVersion,
  "com.h2database" % "h2" % h2Version,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.liquibase" % "liquibase-core" % liquibaseVersion,
  "org.yaml" % "snakeyaml" % snakeYamlVersion,
  "org.webjars" % "jquery" % jqueryVersion,
  "org.webjars" % "normalize.css" % normalizeCssVersion
)

libraryDependencies ++= scope("test",
  "info.cukes" % "cucumber-core" % cucumberVersion,
  "info.cukes" % "cucumber-junit" % cucumberVersion,
  "info.cukes" %% "cucumber-scala" % cucumberVersion,
  "info.cukes" % "cucumber-pro" % cucumberProVersion,
  "junit" % "junit" % junitVersion,
  "com.novocode" % "junit-interface" % junitInterfaceVersion,
  "org.seleniumhq.selenium" % "selenium-java" % seleniumVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion
)

libraryDependencies ++= scope("container,test",
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
  "org.eclipse.jetty" % "jetty-plus" % jettyVersion
)

libraryDependencies ++= scope("provided",
  "javax.servlet" % "javax.servlet-api" % servletApiVersion
)
