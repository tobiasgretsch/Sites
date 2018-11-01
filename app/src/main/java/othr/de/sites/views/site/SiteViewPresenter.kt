package othr.de.sites.views.site

import othr.de.sites.main.MainApp

class SiteViewPresenter(val view: SiteView){

  var app: MainApp

  init {
    app = view.application as MainApp
  }

  fun doCancel() {
    view.finish();
  }

  fun doDelete() {
  }

}