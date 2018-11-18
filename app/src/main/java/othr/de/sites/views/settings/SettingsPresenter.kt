package othr.de.sites.views.settings

import kotlinx.android.synthetic.main.activity_settings_view.*
import othr.de.sites.main.MainApp

class SettingsPresenter(val view: SettingsView) {

  private var app: MainApp

  init {
    app = view.application as MainApp
  }

  val sites = app.sites.findAll()

  fun doShowVisited() {
    val visitedSites = sites.filter { it.visited }
    if (visitedSites.isEmpty()) {
      view.btnTest.text = "No Sites Visited yet"
    } else {
      if(visitedSites.size == 1) {
        view.btnTest.text = ("1 Site visited")
      } else {
        view.btnTest.text = (visitedSites.size.toString() + " Sites visited")
      }
    }
  }

}