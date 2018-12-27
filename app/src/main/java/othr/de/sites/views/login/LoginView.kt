package othr.de.sites.views.login

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login_view.*
import org.jetbrains.anko.toast
import othr.de.sites.R
import othr.de.sites.views.base.BaseView

class LoginView : BaseView() {

  private lateinit var presenter: LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login_view)
    //init(toolbar,false)

    presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

    btnLogin.setOnClickListener {
      val email = login_email.text.toString()
      val password = login_password.text.toString()
      if (email == "" || password == "") {
        toast(R.string.login_emptyEmailPasswordToast)
      } else {
        presenter.doLogin(email, password)
      }
    }

    btn_SignUp.setOnClickListener {
      val email = login_email.text.toString()
      val password = login_password.text.toString()
      if (email == "" || password == "") {
        toast(R.string.login_emptyEmailPasswordToast)
      } else {
        presenter.doSignUp(email, password)
      }
    }
  }

  override fun showProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun onBackPressed() {
  }
}
