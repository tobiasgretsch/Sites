package othr.de.sites.views.login

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_view.*
import othr.de.sites.R
import othr.de.sites.views.base.BaseView

class LoginView : BaseView() {

  private lateinit var presenter: LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login_view)

    presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

    btnLogin.setOnClickListener {
      //TODO password als String bestimmt nicht die beste Lösung
      presenter.doLogin(login_email.text.toString(),login_password.text.toString())
    }
  }

  override fun onBackPressed () {

  }
}
