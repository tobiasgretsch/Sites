package othr.de.sites.views.siteList

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import othr.de.sites.main.MainApp
import othr.de.sites.models.SiteModel
import othr.de.sites.views.site.SiteView

class SiteListPresenter(val view: SiteListView) : AnkoLogger {

  var app: MainApp

  init {
    app = view.application as MainApp
  }

  fun getSites() = app.sites.findAll()

  fun doAddSite() {
    view.startActivityForResult<SiteView>(0)
  }

  fun doEditSite(site: SiteModel) {
    view.startActivityForResult(view.intentFor<SiteView>().putExtra("site_edit",site),0)
  }
}