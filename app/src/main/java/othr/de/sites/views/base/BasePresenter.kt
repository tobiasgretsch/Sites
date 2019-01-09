package othr.de.sites.views.base

import android.content.Intent
import othr.de.sites.main.MainApp

open class BasePresenter(var view: BaseView?) {
  var app: MainApp = view?.application as MainApp

  open fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

  }

  open fun doRequestPermissionsResult(requestCode: Int, permission: Array<String>, grantResults: IntArray) {

  }

  open fun onDestroy() {
    view = null
  }
}