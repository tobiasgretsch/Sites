package othr.de.sites.views.map

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.content_site_maps.*
import othr.de.sites.R
import othr.de.sites.views.base.BaseView


class SiteMapsView : BaseView(), GoogleMap.OnMarkerClickListener {

  lateinit var presenter: SiteMapsPresenter
  lateinit var map: GoogleMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_site_maps)

    presenter = initPresenter(SiteMapsPresenter(this)) as SiteMapsPresenter

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      map = it
      map.setOnMarkerClickListener(this)
      presenter.doConfigureMap(it)
    }
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    presenter.doUpdateCardView(marker)
    return true
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}