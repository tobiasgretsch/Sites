package othr.de.sites.views.site

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import othr.de.sites.R

import kotlinx.android.synthetic.main.activity_site_view.*

class SiteView : AppCompatActivity() {

  lateinit var presenter: SiteViewPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_site_view)
    siteToolbar.title = "Site" //Hier den namen der Angeklickten Site zeigen? Zum Edit/Add eine eigene Activity?
    setSupportActionBar(siteToolbar)

    presenter = SiteViewPresenter(this)
  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_site, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> presenter.doCancel()
      //R.id.item_delete -> presenter.doDelete()
    }
    return super.onOptionsItemSelected(item)
  }

}
