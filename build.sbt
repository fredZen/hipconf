organization := "org.yourorganization"

name := "liftfromscratch"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.2"

seq(webSettings :_*)

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile"
  )
}

libraryDependencies ++= {
  val jettyVersion = "9.1.0.v20131115"
  Seq(
    "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container",
    "org.eclipse.jetty" % "jetty-plus"   % jettyVersion % "container"
  )
}

libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided"
