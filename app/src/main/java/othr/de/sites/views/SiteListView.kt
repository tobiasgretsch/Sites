package othr.de.sites.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_site_list_view.*
import org.jetbrains.anko.toast
import othr.de.sites.R

class SiteListView : AppCompatActivity() {

  lateinit var presenter:SiteListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_site_list_view)

      presenter = SiteListPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }


  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when(item?.itemId) {
      R.id.item_add -> presenter.doAddSite()
    }
    return super.onOptionsItemSelected(item)
  }
}
