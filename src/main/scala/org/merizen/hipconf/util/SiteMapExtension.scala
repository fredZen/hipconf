package org.merizen.hipconf.util

import net.liftweb.sitemap.{Menu, SiteMap, Loc}

trait SiteMapExtension {

  /**
   * insert the menu items at the same level and after the menu that has this LocParam
   */
  val AddAfter: Loc.LocParam[Any]

  /**
   * replace the menu that has this LocParam with the menu items
   */
  val AddHere: Loc.LocParam[Any]

  /**
   * insert the menu items as children of the menu that has this LocParam
   */
  val AddUnder: Loc.LocParam[Any]

  /**
   * The SiteMap mutator function
   */
  def sitemapMutator: SiteMap => SiteMap

  def sitemap: List[Menu]
}
