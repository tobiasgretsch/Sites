package othr.de.sites.views.base

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger
import othr.de.sites.models.SiteModel
import othr.de.sites.views.editLocation.EditLocationView
import othr.de.sites.views.login.LoginView
import othr.de.sites.views.map.SiteMapsView
import othr.de.sites.views.settings.SettingsView
import othr.de.sites.views.site.SiteView
import othr.de.sites.views.siteList.SiteListView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
  LOCATION, SITE, MAP, LIST, SETTINGS, LOGIN
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {
  var basePresenter: BasePresenter? = null;

  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    var intent = Intent(this, SiteListView::class.java)
    when (view) {
      VIEW.LIST -> intent = Intent(this, SiteListView::class.java)
      VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
      VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
      VIEW.MAP -> intent = Intent(this, SiteMapsView::class.java)
      VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
      VIEW.SITE -> intent = Intent(this, SiteView::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init(toolbar: Toolbar, upEnabled: Boolean) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showSite(site: SiteModel) {}
  open fun showSites(sites: List<SiteModel>) {}
  open fun showProgress() {}
  open fun hideProgress() {}
}