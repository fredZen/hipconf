package cucumber.util

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext

object StartStopAppHooks extends GlobalCucumberHooks {
  private var server: Server = _

  BeforeAll {
    server = new Server(8080)
    val context = new WebAppContext()
    context.setServer(server)
    context.setContextPath("/")
    context.setWar("src/main/webapp")
    server.setHandler(context)
    server.start()
  }

  AfterAll {
    server.stop()
  }
}
