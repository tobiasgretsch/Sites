package othr.de.sites.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import othr.de.sites.R
import othr.de.sites.views.base.BaseView

class SettingsView : BaseView() {

  private lateinit var presenter : SettingsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings_view)

    presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

    presenter.doShowVisited()
    presenter.doShowNumberOfSites()

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_settings, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_settings_back -> presenter.doFinish()
    }
    return super.onOptionsItemSelected(item)
  }

}
