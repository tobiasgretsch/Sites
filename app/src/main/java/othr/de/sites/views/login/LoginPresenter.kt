package othr.de.sites.views.login

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import othr.de.sites.R
import othr.de.sites.views.base.BasePresenter
import othr.de.sites.views.base.BaseView
import othr.de.sites.views.base.VIEW

class LoginPresenter (view: BaseView): BasePresenter (view),AnkoLogger {

  fun doLogin(email: String, password: String) {
    if(email.isEmpty() && password.isEmpty()) {
      view?.toast(R.string.login_emptyEmailPasswordToast)
    } else {
      if(email == "1" && password == "1")
        view?.navigateTo(VIEW.LIST)


      //TODO : Login Action und prüfen der richtigkeit der Daten passiert hier
    }
  }
  //TODO if no User found with the entered Email, show message and create new User with default start Set of Sites
}
