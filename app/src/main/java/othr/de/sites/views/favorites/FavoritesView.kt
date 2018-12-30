package othr.de.sites.views.favorites

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_favorites_view.*
import kotlinx.android.synthetic.main.activity_site_list_view.*
import othr.de.sites.R
import othr.de.sites.models.SiteModel
import othr.de.sites.views.base.BaseView
import othr.de.sites.views.siteList.SiteAdapter
import othr.de.sites.views.siteList.SiteListener

class FavoritesView: BaseView(), SiteListener {
  lateinit var presenter: FavoritesPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_favorites_view)

    presenter = initPresenter(FavoritesPresenter(this)) as FavoritesPresenter

    val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    favoritesRecyclerView.layoutManager = layoutManager
    presenter.loadSites()
  }

  override fun showSites(sites: List<SiteModel>) {
    favoritesRecyclerView.adapter = SiteAdapter(sites,this)
    favoritesRecyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onSiteClick(site: SiteModel) {
    presenter.doEditSite(site)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadSites()
    super.onActivityResult(requestCode, resultCode, data)
  }
}