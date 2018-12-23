package othr.de.sites.views.site

import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.content_site_view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import othr.de.sites.R
import othr.de.sites.helpers.*
import othr.de.sites.models.Location
import othr.de.sites.models.SiteModel
import othr.de.sites.views.base.BasePresenter
import othr.de.sites.views.base.BaseView
import othr.de.sites.views.base.VIEW
import java.text.SimpleDateFormat
import java.util.*


class SitePresenter(view: BaseView) : AnkoLogger, BasePresenter(view) {

  private val IMAGE_REQUEST = 1
  private val LOCATION_REQUEST = 2

  var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
  val locationRequest = createDefaultLocationRequest()

  var site = SiteModel()
  var edit = false
  //private var location = Location(40.0, 0.0, 10f)

  init {
    if (view.intent.hasExtra("site_edit")) {
      edit = true
      site = view.intent.extras.getParcelable<SiteModel>("site_edit")
      view.showSite(site)
      if (site.visited) {
        view.siteCheckBox.isChecked = true
        view.siteCheckBox.isEnabled = false
      }
    } else {
      if (checkLocationPermissions(view)) {
        doSetCurrentLocation()
      }
    }
  }

  override fun doRequestPermissionsResult(requestCode: Int, permission: Array<String>, grantResults: IntArray) {
    if (isPermissionGranted(requestCode, grantResults)) {
      doSetCurrentLocation()
    }
  }

  @SuppressLint("MissingPermission")
  fun doSetCurrentLocation() {
    locationService.lastLocation.addOnSuccessListener {
      site.latitute = it.latitude
      site.longtitue = it.longitude
    }
  }

  fun doAddorEditSite(name: String, description: String, additionalInfo: String) {
    if (name.isEmpty()) {
      view?.toast(R.string.site_emptyTitleToast)
    } else {
      site.name = name
      site.description = description
      site.visited = view!!.siteCheckBox.isChecked
      site.additionalInfo = additionalInfo
      if (edit) {
        app.sites.update(site)
      } else {
        app.sites.create(site)
      }
      view?.finish()
    }
  }

  fun doCancel() {
    view?.finish()
  }

  fun doDelete() {
    app.sites.delete(site)
    view?.finish()
  }

  fun doChangeCheckBox() {
    if (view!!.siteCheckBox.isChecked) {
      view!!.siteCheckBox.isEnabled = false
    }
    val now = SimpleDateFormat("d MMM yyyy, hh:mm", Locale.GERMAN).format(Date())
    view!!.siteDateVisited.text = now

    site.date_visited = now
  }

  fun doSelectImage() {
    //TODO 4 Bilder auswählen! Wie die Bilder auf der SiteView anzeigen?
    showImagePicker(view!!, IMAGE_REQUEST)
  }

  fun doShowMap() {
    doSetCurrentLocation()
    view?.navigateTo(
      VIEW.LOCATION,
      LOCATION_REQUEST,
      "location",
      Location(site.longtitue, site.latitute, site.zoom, site.name)
    )
  }


  @SuppressLint("MissingPermission")
  fun doRestartLocationUpdates() {
    val locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult != null && locationResult.locations != null) {
          val l = locationResult.locations.last()
          site.latitute = l.latitude
          site.longtitue = l.longitude
        }
      }
    }
    if (!edit) {
      locationService.requestLocationUpdates(locationRequest, locationCallback, null)
    }
  }

  override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        site.images = data.data.toString()
        view!!.siteImage.setImageBitmap(readImage(view!!, resultCode, data))
        view!!.addImage.setText(R.string.site_changeImage)
      }
      LOCATION_REQUEST -> {
        val location = data.extras.getParcelable<Location>("location")
        site.latitute = location!!.latitute
        site.longtitue = location.longtitue
        site.zoom = location.zoom

      }
    }
  }

  fun doSetImageEmtyString(): Boolean {
    site.images = ""
    view!!.siteImage.setImageBitmap(readImageFromPath(view!!, site.images))
    view!!.siteImage.setImageResource(R.drawable.splashscreen) //TODO hier ein plus Ican rein setzen
    return true
  }

  fun doManageImages() {
    when (site.images) { //TODO site.images.size
      //TODO Wenn Kein Bild da Icon bei [0] setzen
      //bei 1 -> Bild setzen auf [0] und [1] anzeigen mit Plus
      //bei 2 -> Bild setzen auf [1] und [2] anzeigen mit Plus usw
      //Funktion durchlaufen die überprüft ob zwischen den Bildern leere Strings sind
      //FIXME bei site Liste mit 4 Strings? Wie dann die [when] Bedingung setzen wenn die size immer 4 ist?
      //
    }
  }
}