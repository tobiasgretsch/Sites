package othr.de.sites.views.login

import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import othr.de.sites.models.firebase.SiteFireStore
import othr.de.sites.views.base.BasePresenter
import othr.de.sites.views.base.BaseView
import othr.de.sites.views.base.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view), AnkoLogger {

  var auth: FirebaseAuth = FirebaseAuth.getInstance()
  var fireStore: SiteFireStore? = null

  init {
    if (app.sites is SiteFireStore) {
      fireStore = app.sites as SiteFireStore
    }
  }

  fun doLogin(email: String, password: String) {
    view?.showProgress()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        if (fireStore != null) {
          fireStore!!.fetchSites {
            view?.hideProgress()
            view?.navigateTo(VIEW.LIST)
          }
        } else {
          view?.hideProgress()
          view?.navigateTo(VIEW.LIST)
        }
      } else {
        view?.hideProgress()
        view?.toast("Login Failed: ${task.exception?.message}")
      }
      view?.hideProgress()
    }
  }

  fun doSignUp(email: String, password: String) {
    view?.showProgress()
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        view?.navigateTo(VIEW.LIST)
      } else {
        view?.toast("Sign up Failed: ${task.exception?.message}")
      }
      view?.hideProgress()
    }

    //TODO neue Liste von Default Sites dem User zuordnen / kopieren

  }
}
