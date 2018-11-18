package othr.de.sites.views.settings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings_view.*
import othr.de.sites.R

class SettingsView : AppCompatActivity() {

  private lateinit var presenter : SettingsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings_view)

    presenter = SettingsPresenter(this)

    btnTest.setOnClickListener() {
      presenter.doShowVisited()
    }
  }


}
