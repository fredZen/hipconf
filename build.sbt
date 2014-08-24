organization := "org.merizen"

name := "hipconf"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

val liftVersion = "2.6-RC1"
val h2Version = "1.3.176"
val logbackVersion = "1.1.2"
val cucumberVersion = "1.1.8"
val junitVersion = "4.11"
val seleniumVersion = "2.42.2"
val scalatestVersion = "2.2.1"
val cucumberProVersion = "1.0.10"
val jettyVersion = "9.1.0.v20131115"
val servletApiVersion = "3.0.1"

webSettings

def scope(scopeName: String, deps: ModuleID*) = deps map (_ % scopeName)

libraryDependencies ++= scope("compile",
  "net.liftweb" %% "lift-webkit" % liftVersion,
  "net.liftweb" %% "lift-squeryl-record" % liftVersion,
  "com.h2database" % "h2" % h2Version,
  "ch.qos.logback" % "logback-classic" % logbackVersion
)

libraryDependencies ++= scope("test",
  "info.cukes" % "cucumber-core" % cucumberVersion,
  "info.cukes" % "cucumber-junit" % cucumberVersion,
  "info.cukes" %% "cucumber-scala" % cucumberVersion,
  "info.cukes" % "cucumber-pro" % cucumberProVersion,
  "junit" % "junit" % junitVersion,
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
