package bootstrap.liftweb

import net.liftweb.http.LiftRules
import net.liftweb.sitemap.{Menu, SiteMap}
import org.merizen.hipconf.user.User

object SiteMapConfiguration extends Configuration {
  private val siteMapTemplate =
    SiteMap(
      Menu.i("Home") / "index" >> User.AddUserMenusAfter
    )

  override def setup(): Unit = {
    LiftRules.setSiteMap(siteMap)
  }

  private def siteMap: SiteMap = {
    siteMapMutator(siteMapTemplate)
  }

  private def siteMapMutator = User.sitemapMutator
}
