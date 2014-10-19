package org.merizen.hipconf.user

import net.liftweb.sitemap.Loc.LocParam
import net.liftweb.sitemap.{Menu, SiteMap}
import org.merizen.hipconf.util.{SiteMapExtension, HipConfModule}

object Module extends HipConfModule {
  object menu extends SiteMapExtension {
    val AddAfter: LocParam[Any] = User.AddUserMenusAfter
    val AddHere: LocParam[Any] = User.AddUserMenusHere
    val AddUnder: LocParam[Any] = User.AddUserMenusUnder

    def sitemapMutator: (SiteMap) => SiteMap = User.sitemapMutator

    def sitemap: List[Menu] = User.sitemap
  }
}
