import sbt._

object Dependencies {
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

  object d {
    object lift {
      val webkit = "net.liftweb" %% "lift-webkit" % liftVersion
      val squerylRecord = "net.liftweb" %% "lift-squeryl-record" % liftVersion
    }

    // Back-end
    val servletApi = "javax.servlet" % "javax.servlet-api" % servletApiVersion
    object jetty {
      val webapp = "org.eclipse.jetty" % "jetty-webapp" % jettyVersion
      val plus = "org.eclipse.jetty" % "jetty-plus" % jettyVersion
      val runner = "org.eclipse.jetty" % "jetty-runner" % jettyVersion
    }

    // Database
    val h2 = "com.h2database" % "h2" % h2Version
    val liquibaseCore = "org.liquibase" % "liquibase-core" % liquibaseVersion
    val snakeYaml = "org.yaml" % "snakeyaml" % snakeYamlVersion

    // Logging
    val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVersion
    val groovy = "org.codehaus.groovy" % "groovy" % groovyVersion

    // Front-end
    val jquery = "org.webjars" % "jquery" % jqueryVersion
    val normalizeCss = "org.webjars" % "normalize.css" % normalizeCssVersion

    // Testing
    object cucumber {
      val core = "info.cukes" % "cucumber-core" % cucumberVersion
      val junit = "info.cukes" % "cucumber-junit" % cucumberVersion
      val scala = "info.cukes" %% "cucumber-scala" % cucumberVersion
      val pro = "info.cukes" % "cucumber-pro" % cucumberProVersion
    }
    val junit = "junit" % "junit" % junitVersion
    val junitInterface = "com.novocode" % "junit-interface" % junitInterfaceVersion
    val scalatest = "org.scalatest" %% "scalatest" % scalatestVersion
    val seleniumJava = "org.seleniumhq.selenium" % "selenium-java" % seleniumVersion
  }
}
