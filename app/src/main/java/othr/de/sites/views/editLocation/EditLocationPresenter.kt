package othr.de.sites.views.editLocation

import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import othr.de.sites.models.Location
import othr.de.sites.views.base.BasePresenter
import othr.de.sites.views.base.BaseView

class EditLocationPresenter(view: BaseView) : BasePresenter(view) {

  var location = Location()

  init {
    location = view.intent.extras.getParcelable<Location>("location")
  }

  fun initMap(map: GoogleMap) {
    map.clear()
    val loc = LatLng(location.latitute, location.longtitue)
    val options = MarkerOptions()
      .title(location.titel)
      .snippet("GPS : " + loc.toString())
      .draggable(true)
      .position(loc)
    map.addMarker(options)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
  }

  fun doUpdateLocation(lat: Double, lng: Double, zoom: Float) {
    location.latitute = lat
    location.longtitue = lng
    location.zoom = zoom
  }

  fun doUpdateLocation(lat: Double, lng: Double) {
    location.latitute = lat
    location.longtitue = lng
  }

  fun doOnBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    view?.setResult(Activity.RESULT_OK, resultIntent)
    view?.finish()
  }

  fun doUpdateMarker(marker: Marker) {
    val loc = LatLng(location.latitute, location.longtitue)
    marker.setSnippet("GPS : " + loc.toString())
  }

}