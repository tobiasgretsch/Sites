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
      view.settings_sites_visited.text = "No Sites Visited yet"
    } else {
      view.settings_sites_visited.text = visitedSites.size.toString()
    }
  }

  fun doShowNumberOfSites() {
    view.settings_number_of_sites.text = sites.size.toString();
  }

  fun doFinish() {
    view.finish()
  }

}