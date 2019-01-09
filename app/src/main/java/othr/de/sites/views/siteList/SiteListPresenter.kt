package othr.de.sites.views.siteList

import com.google.firebase.auth.FirebaseAuth
import othr.de.sites.models.SiteModel
import othr.de.sites.views.base.BasePresenter
import othr.de.sites.views.base.BaseView
import othr.de.sites.views.base.VIEW

class SiteListPresenter(view: BaseView) : BasePresenter(view) {

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
    FirebaseAuth.getInstance().signOut()
    app.sites.clear()
    view?.navigateTo(VIEW.LOGIN)
  }

  fun doShowMap() {
    view?.navigateTo(VIEW.MAP)
  }

  fun loadSites() {
    view?.showSites(app.sites.findAll())
  }

  fun doShowFavorites() {
    view?.navigateTo(VIEW.FAVORITES)
  }
}