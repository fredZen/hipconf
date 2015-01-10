package cucumber.endtoend

import java.io.InputStream
import java.util.Properties

import cucumber.util.GlobalCucumberHooks
import org.apache.commons.io.IOUtils
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext

object StartStopAppHooks extends GlobalCucumberHooks {
  private[this] var server: Server = _

  BeforeAll {
    server = new Server(8080)
    val context = new WebAppContext()
    context.setServer(server)
    context.setContextPath("/")
    context.setWar(webappPath)
    server.setHandler(context)
    server.start()
  }

  AfterAll {
    server.stop()
  }

  private def webappPath: String = {
    val p = new Properties
    var in:InputStream = null
    try {
      in = getClass.getClassLoader.getResourceAsStream("webapp.properties")
      p.load(in)
      p.getProperty("webappDir")
    } finally {
      IOUtils.closeQuietly(in)
    }
  }
}
