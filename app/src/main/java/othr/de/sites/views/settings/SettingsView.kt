package othr.de.sites.views.settings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings_view.*
import othr.de.sites.R

class SettingsView : AppCompatActivity() {

  private lateinit var presenter : SettingsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings_view)

    presenter = SettingsPresenter(this)

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
