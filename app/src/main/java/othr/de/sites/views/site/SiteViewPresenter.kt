package othr.de.sites.views.site

import android.content.Intent
import kotlinx.android.synthetic.main.content_site_view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import othr.de.sites.helpers.showImagePicker
import othr.de.sites.main.MainApp
import othr.de.sites.models.Location
import othr.de.sites.models.SiteModel
import othr.de.sites.views.editLocation.EditLocationView
import java.text.SimpleDateFormat
import java.util.*


class SiteViewPresenter(val view: SiteView) {

  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  var site = SiteModel()
  var app: MainApp
  var edit = false
  var location = Location(40.0, 0.0, 10f)

  init {
    app = view.application as MainApp
    if (view.intent.hasExtra("site_edit")) {
      edit = true
      site = view.intent.extras.getParcelable<SiteModel>("site_edit")
      view.showSite(site)
      if (site.visited == true) {
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
    } else {
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

  fun doChangeCheckBox() {
    if(view.siteCheckBox.isChecked) {
      view.siteCheckBox.setEnabled(false)
    }
    val now = SimpleDateFormat("d MMM yyyy, hh:mm").format(Date())
    view.siteDateVisited.text = now

    site.date_visited = now
  }

  fun doSelectImage() {
    showImagePicker(view, IMAGE_REQUEST)
  }

  fun doShowMap() {
    if(site.zoom != 0f) {
      location.latitute = site.latitute
      location.longtitue = site.longtitue
      location.zoom = site.zoom
    }
    view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location",location), LOCATION_REQUEST)
  }

  fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          site.images = data.data.toString()
          view.showSite(site)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras.getParcelable<Location>("location")
          site.latitute = location.latitute
          site.longtitue = location.longtitue
          site.zoom = location.zoom
        }
      }
    }
  }
}