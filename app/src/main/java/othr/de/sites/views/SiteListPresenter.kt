package othr.de.sites.views

import othr.de.sites.main.MainApp

class SiteListPresenter (val view: SiteListView) {

    var app : MainApp

    init {
        app = view.application as MainApp
    }


}