package othr.de.sites.views.map

import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_site_maps.*
import othr.de.sites.helpers.readImageFromPath
import othr.de.sites.models.SiteModel
import othr.de.sites.views.base.BasePresenter
import othr.de.sites.views.base.BaseView

class SiteMapsPresenter(view: BaseView) : BasePresenter(view) {

  fun doConfigureMap(map: GoogleMap) {
    map.uiSettings.isZoomControlsEnabled = true
    val sites = app.sites.findAll()
    sites.forEach {
      val loc = LatLng(it.latitude, it.longitude)
      val options = MarkerOptions().title(it.name).position(loc)
      map.addMarker(options).tag = it
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
    }
    if (sites.isNotEmpty()) {
      val site = sites[sites.lastIndex]
      setCardViewValues(site)
    }
  }

  fun doUpdateCardView(marker: Marker) {
    val site = marker.tag as SiteModel
    //val site = app.sites.findById(tag)
    setCardViewValues(site)
  }

  fun setCardViewValues(site: SiteModel?) {
    view!!.currentTitle.text = site!!.name
    view!!.currentDescription.text = site.description
    if (!site.images.isEmpty()) Glide.with(view!!).load(site.images[0]).into(view!!.imageViewMap)
    //view!!.imageViewMap.setImageBitmap(readImageFromPath(view!!, site.images))
  }
}
