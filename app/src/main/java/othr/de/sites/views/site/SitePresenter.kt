package othr.de.sites.views.site

import android.content.Intent
import kotlinx.android.synthetic.main.content_site_view.*
import org.jetbrains.anko.intentFor
import othr.de.sites.R
import othr.de.sites.helpers.readImage
import othr.de.sites.helpers.showImagePicker
import othr.de.sites.main.MainApp
import othr.de.sites.models.Location
import othr.de.sites.models.SiteModel
import othr.de.sites.views.editLocation.EditLocationView
import java.text.SimpleDateFormat
import java.util.*


class SitePresenter(val view: SiteView){

  private val IMAGE_REQUEST = 1
  private val LOCATION_REQUEST = 2

  var site = SiteModel()
  var app: MainApp = view.application as MainApp
  var edit = false
  private var location = Location(40.0, 0.0, 10f)

  init {
    if (view.intent.hasExtra("site_edit")) {
      edit = true
      site = view.intent.extras.getParcelable<SiteModel>("site_edit")
      view.showSite(site)
      if (site.visited) {
        view.siteCheckBox.isChecked = true
        view.siteCheckBox.isEnabled = false
      }
      view.addSite.setText(R.string.save_site)
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
    if (view.siteCheckBox.isChecked) {
      view.siteCheckBox.isEnabled = false
    }
    val now = SimpleDateFormat("d MMM yyyy, hh:mm", Locale.GERMAN).format(Date())
    view.siteDateVisited.text = now

    site.date_visited = now
  }

  fun doSelectImage() {
    //TODO 4 Bilder auswählen! Wie die Bilder auf der SiteView anzeigen?
    showImagePicker(view, IMAGE_REQUEST)
  }

  fun doShowMap() {
    if (site.zoom != 0f) {
      location.latitute = site.latitute
      location.longtitue = site.longtitue
      location.zoom = site.zoom
    }
    if (site.defaultLocation) {
      site.defaultLocation = false
    }

    view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location", location), LOCATION_REQUEST)
  }

  fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        site.images = data.data.toString()
        view.siteImage.setImageBitmap(readImage(view, resultCode, data))
        view.addImage.setText(R.string.change_site_image)
      }
      LOCATION_REQUEST -> {
        val location = data.extras.getParcelable<Location>("location")
        site.latitute = location!!.latitute
        site.longtitue = location.longtitue
        site.zoom = location.zoom

      }
    }
  }
}