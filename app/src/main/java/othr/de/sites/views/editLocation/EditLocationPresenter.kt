package othr.de.sites.views.editLocation

import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import othr.de.sites.models.Location

class EditLocationPresenter(val activity: EditLocationView) {

  var location = Location()

  init {
    location = activity.intent.extras.getParcelable<Location>("location")
  }

  fun initMap(map: GoogleMap) {
    val loc = LatLng(location.latitute, location.longtitue)
    val options = MarkerOptions()
      .title("Site")
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

  fun doOnBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    activity.setResult(Activity.RESULT_OK, resultIntent)
    activity.finish()
  }

  fun doUpdateMarker(marker: Marker) {
    val loc = LatLng(location.latitute, location.longtitue)
    marker.setSnippet("GPS : " + loc.toString())
  }
}