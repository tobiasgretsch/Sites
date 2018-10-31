package othr.de.sites.views

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivityForResult
import othr.de.sites.main.MainApp

class SiteListPresenter (val view: SiteListView) : AnkoLogger{

    var app : MainApp

    init {
        app = view.application as MainApp
    }

    fun doAddSite() {
        view.startActivityForResult<SiteView>(0)
    }
}