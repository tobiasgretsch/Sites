package othr.de.sites.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings_view.*
import othr.de.sites.R
import othr.de.sites.views.base.BaseView

class SettingsView : BaseView() {

  private lateinit var presenter : SettingsPresenter
  private var user = FirebaseAuth.getInstance().currentUser

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings_view)

    presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

    presenter.doShowVisited()
    presenter.doShowNumberOfSites()

    settings_email.text = user?.email
    settings_password.text = "Passwort anzeigen?"

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
