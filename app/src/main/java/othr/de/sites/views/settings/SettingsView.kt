package othr.de.sites.views.settings

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings_view.*
import othr.de.sites.R
import othr.de.sites.views.base.BaseView

class SettingsView : BaseView() {

  private lateinit var presenter: SettingsPresenter
  private var user = FirebaseAuth.getInstance().currentUser

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings_view)

    presenter = initPresenter(SettingsPresenter(this)) as SettingsPresenter

    presenter.doShowVisited()
    presenter.doShowNumberOfSites()

    settings_email.text = user!!.email
  }
}
