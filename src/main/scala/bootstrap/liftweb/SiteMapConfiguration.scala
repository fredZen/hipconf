package bootstrap.liftweb

import net.liftweb.http.LiftRules
import net.liftweb.sitemap.Loc.LocParam
import net.liftweb.sitemap.{Menu, SiteMap}
import org.merizen.hipconf._
import org.merizen.hipconf.util.HipConfModule

import scala.language.implicitConversions

object SiteMapConfiguration extends Configuration with Modules {
  private val siteMapTemplate =
    SiteMap(
      Menu.i("Home") / "index" >> user.Module
    )

  def setup(): Unit = {
    LiftRules.setSiteMap(siteMap)
  }

  private def siteMap: SiteMap = {
    siteMapMutator(siteMapTemplate)
  }

  private def siteMapMutator = modules.foldLeft(identity[SiteMap] _)(_ andThen _.menu.sitemapMutator)

  private implicit def locParam(m: HipConfModule): LocParam[Any] = m.menu.AddAfter
}
