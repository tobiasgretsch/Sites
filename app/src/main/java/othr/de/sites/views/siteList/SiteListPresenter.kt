package othr.de.sites.views.siteList

import android.support.v7.widget.RecyclerView
import othr.de.sites.models.SiteModel
import othr.de.sites.views.base.BasePresenter
import othr.de.sites.views.base.BaseView
import othr.de.sites.views.base.VIEW

class SiteListPresenter(view: BaseView) :BasePresenter(view){

  fun doAddSite() {
    view?.navigateTo(VIEW.SITE)
  }

  fun doEditSite(site: SiteModel) {
    view?.navigateTo(VIEW.SITE, 0, "site_edit", site)
  }

  fun doOpenSettings() {
    view?.navigateTo(VIEW.SETTINGS)
  }

  fun doLogout() {
    view?.navigateTo(VIEW.LOGIN)
  }

  fun doShowMap() {
    view?.navigateTo(VIEW.MAP)
  }

  fun doSmoothScroll(recyclerView: RecyclerView) {
    if(app.sites.findAll().isNotEmpty()) {
      recyclerView.smoothScrollToPosition(0)
    }
  }

  fun loadSites(){
    view?.showSites(app.sites.findAll())
  }
}