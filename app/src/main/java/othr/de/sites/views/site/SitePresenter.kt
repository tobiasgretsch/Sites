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
      site.latitude = it.latitude
      site.longitude = it.longitude
    }
  }

  fun doAddorEditSite(name: String, description: String, additionalInfo: String, rating: Float) {
    if (name.isEmpty()) {
      view?.toast(R.string.site_emptyTitleToast)
    } else {
      site.name = name
      site.description = description
      site.visited = view!!.siteCheckBox.isChecked
      site.additionalInfo = additionalInfo
      site.rating = rating
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
    val now = SimpleDateFormat("d MMM", Locale.GERMAN).format(Date())
    view!!.siteDateVisited.text = now

    site.date_visited = now
  }

  fun doSelectImage() {
    if (site.images.size >= 4) {
      view!!.toast(R.string.site_changeImage)
    } else {
      showImagePicker(view!!, IMAGE_REQUEST)
    }
  }

  fun doShowMap() {
    doSetCurrentLocation()
    view?.navigateTo(
      VIEW.LOCATION, LOCATION_REQUEST, "location",
      Location(site.longitude, site.latitude, site.zoom, site.name)
    )
  }


  @SuppressLint("MissingPermission")
  fun doRestartLocationUpdates() {
    val locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult != null) {
          val l = locationResult.locations.last()
          site.latitude = l.latitude
          site.longitude = l.longitude
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
        if (!site.images.contains(data.data.toString())) {
          site.images.add(data.data.toString())
        }
        view!!.recyclerViewImages.adapter = ImagesAdapter(site.images, view!! as SiteView)
        view!!.recyclerViewImages.adapter?.notifyDataSetChanged()
      }
      LOCATION_REQUEST -> {
        val location = data.extras.getParcelable<Location>("location")
        site.latitude = location!!.latitute
        site.longitude = location.longtitue
        site.zoom = location.zoom

      }
    }
  }

  fun doChangeFavoriteCheckBox() {
    site.favorite = view!!.favoritesCheckBox.isChecked
  }

  fun doShareSite() {
    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_TEXT, site.toString())
    println(site.toString())
    shareIntent.type = "text/plain"

    view!!.startActivity(Intent.createChooser(shareIntent, "Share Site to:"))
  }

  fun doDeleteImage(image: String) {
    site.images.remove(image)

    view!!.recyclerViewImages.adapter = ImagesAdapter(site.images, view!! as SiteView)
    view!!.recyclerViewImages.adapter?.notifyDataSetChanged()
  }
}