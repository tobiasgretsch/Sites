package othr.de.sites.views.site

import android.content.Intent
import kotlinx.android.synthetic.main.activity_site_view.*
import kotlinx.android.synthetic.main.content_site_view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.placemark.helpers.showImagePicker
import othr.de.sites.main.MainApp
import othr.de.sites.models.SiteModel


class SiteViewPresenter(val view: SiteView) {

  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  var site = SiteModel()
  var app: MainApp
  var edit = false

  init {
    app = view.application as MainApp
    if (view.intent.hasExtra("site_edit")) {
      edit = true
      site = view.intent.extras.getParcelable<SiteModel>("site_edit")
      view.showSite(site)
      if(site.visited == true) {
        view.siteCheckBox.setChecked(true)
        view.siteCheckBox.setEnabled(false)
      }
      view.addSite.text = "Save Site"
    }
  }

  fun doAddorEditSite(name: String, description: String) {
    site.name = name
    site.description = description
    site.visited = view.siteCheckBox.isChecked

    if (edit) {
      app.sites.update(site)
    }
    else {
      app.sites.create(site)
    }
    view.finish()
  }

  fun doCancel() {
    view.finish()
  }

  fun doDelete() {
    app.sites.delete(site)
    view.finish()
  }

  fun doSelectImage() {
    showImagePicker(view, IMAGE_REQUEST)
  }

  fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when(requestCode) {
      IMAGE_REQUEST -> {
        site.images = data.data.toString()
        view.showSite(site)
       // view.siteImage.;
      }
    }
  }
}