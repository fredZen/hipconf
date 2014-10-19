package org.merizen.hipconf.session

import net.liftweb.sitemap.Loc.Hidden
import net.liftweb.sitemap.Menu
import org.merizen.hipconf.util.{SiteMapExtender, HipConfModule}

object Module extends HipConfModule{
  object menu extends SiteMapExtender {
    def sitemap: List[Menu] = List(
      Menu.i("menu.session") / "session" / "index" >> Hidden submenus (
        Menu.i("menu.submit_session") / "session" / "submit"
      )
    )
  }
}
