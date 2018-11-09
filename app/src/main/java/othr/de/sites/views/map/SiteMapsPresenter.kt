package othr.de.sites.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_site_view.*
import othr.de.sites.helpers.readImageFromPath
import othr.de.sites.main.MainApp


class SiteMapsPresenter (val view: SiteMapsView) {

  var app: MainApp

  init {
    app = view.application as MainApp

  }

  fun doConfigureMap(map: GoogleMap) {
    map.uiSettings.setZoomControlsEnabled(true)
    map.setOnMarkerClickListener(view)
    app.sites.findAll().forEach {
      val loc = LatLng(it.latitute, it.longtitue)
      val options = MarkerOptions().title(it.name).position(loc)
      map.addMarker(options).tag = it.id
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
    }
  }

  fun doUpdateCardView(marker: Marker) {
    val tag = marker.tag as Long
    val site = app.sites.findById(tag)
    view.currentTitle.text = site!!.name
    view.currentDescription.text = site!!.description
    view.imageView.setImageBitmap(readImageFromPath(view, site!!.images))
  }
}