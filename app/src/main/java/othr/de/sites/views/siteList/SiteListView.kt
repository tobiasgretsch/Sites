package othr.de.sites.views.siteList

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_site_list_view.*
import othr.de.sites.R
import othr.de.sites.models.SiteModel
import othr.de.sites.views.base.BaseView

class SiteListView : BaseView(), SiteListener {

  lateinit var presenter: SiteListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_site_list_view)

    presenter = initPresenter(SiteListPresenter(this)) as SiteListPresenter

    val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadSites();

  }

  override fun showSites(sites: List<SiteModel>) {
    recyclerView.adapter = SiteAdapter(sites, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_list, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddSite()
      R.id.item_up -> presenter.doSmoothScroll(recyclerView)
      R.id.item_settings -> presenter.doOpenSettings()
      R.id.item_logout -> presenter.doLogout()
      R.id.item_map -> presenter.doShowMap()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onSiteClick(site: SiteModel) {
    presenter.doEditSite(site)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadSites()
    super.onActivityResult(requestCode, resultCode, data)
  }
}
