package org.merizen.hipconf.util

import net.liftweb.sitemap.{Menu, SiteMap, Loc}

trait SiteMapExtender extends SiteMapExtension {

  case object AddAfter extends Loc.LocParam[Any]

  case object AddHere extends Loc.LocParam[Any]

  case object AddUnder extends Loc.LocParam[Any]


  private lazy val AfterUnapply = SiteMap.buildMenuMatcher(_ == AddAfter)
  private lazy val HereUnapply = SiteMap.buildMenuMatcher(_ == AddHere)
  private lazy val UnderUnapply = SiteMap.buildMenuMatcher(_ == AddUnder)

  /**
   * The SiteMap mutator function
   */
  def sitemapMutator: SiteMap => SiteMap = SiteMap.sitemapMutator {
    case AfterUnapply(menu) => menu :: sitemap
    case HereUnapply(_) => sitemap
    case UnderUnapply(menu) => List(menu.rebuild(_ ::: sitemap))
  }(SiteMap.addMenusAtEndMutator(sitemap))

  def sitemap: List[Menu]

}
