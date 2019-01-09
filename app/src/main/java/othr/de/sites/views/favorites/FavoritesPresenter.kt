package othr.de.sites.views.favorites

import othr.de.sites.models.SiteModel
import othr.de.sites.views.base.BasePresenter
import othr.de.sites.views.base.BaseView
import othr.de.sites.views.base.VIEW

class FavoritesPresenter(view: BaseView) : BasePresenter(view) {

  fun loadSites() {
    val filteredSites = app.sites.findAll().filter { it.favorite }
    view?.showSites(filteredSites)
  }

  fun doEditSite(site: SiteModel) {
    view?.navigateTo(VIEW.SITE, 0, "site_edit", site)
  }

}
