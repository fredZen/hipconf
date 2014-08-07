organization := "org.yourorganization"

name := "liftfromscratch"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

seq(webSettings :_*)

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion
  ) map (_ % "compile")
}

libraryDependencies ++= {
  val jettyVersion = "9.1.0.v20131115"
  Seq(
    "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
    "org.eclipse.jetty" % "jetty-plus"   % jettyVersion
  ) map (_ % "container")
}

libraryDependencies ++= {
  val cucumberVersion = "1.1.8"
  val junitVersion = "4.11"
  Seq(
    "info.cukes" %  "cucumber-core"  % cucumberVersion,
    "info.cukes" %  "cucumber-junit" % cucumberVersion,
    "info.cukes" %% "cucumber-scala" % cucumberVersion,
    "junit"      %  "junit"          % junitVersion
  ) map (_ % "test")
}

libraryDependencies ++= {
  val servletApiVersion = "3.0.1"
  Seq(
    "javax.servlet" % "javax.servlet-api" % servletApiVersion
  ) map (_ % "provided")
}
