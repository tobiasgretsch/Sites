package othr.de.sites.views.site

import kotlinx.android.synthetic.main.activity_site_view.*
import kotlinx.android.synthetic.main.content_site_view.*
import org.jetbrains.anko.AnkoLogger
import othr.de.sites.main.MainApp
import othr.de.sites.models.SiteModel


class SiteViewPresenter(val view: SiteView) {

  var app: MainApp
  var site = SiteModel()
  var edit = false

  init {
    app = view.application as MainApp
    if (view.intent.hasExtra("site_edit")) {
      edit = true
      site = view.intent.extras.getParcelable<SiteModel>("site_edit")
      view.showSite(site)
      view.addSite.text = "Save Site"
    }
  }

  fun doCancel() {
    view.finish()
  }

  fun doDelete() {
    app.sites.delete(site)
    view.finish()
  }

  fun doAddorEditSite(name: String, description: String) {
    site.name = name
    site.description = description
    if (edit) {
      app.sites.update(site)
    } else {
      app.sites.create(site)
    }
    view.finish()
  }
}