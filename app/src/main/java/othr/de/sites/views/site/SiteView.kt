package othr.de.sites.views.site

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import othr.de.sites.R

import kotlinx.android.synthetic.main.activity_site_view.*
import kotlinx.android.synthetic.main.content_site_view.*
import othr.de.sites.helpers.readImageFromPath
import othr.de.sites.models.SiteModel

class SiteView : AppCompatActivity() {

  lateinit var presenter: SiteViewPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_site_view)
    siteToolbar.title = "Site" //Hier den namen der Angeklickten Site zeigen? Zum Edit/Add eine eigene Activity?
    setSupportActionBar(siteToolbar)

    presenter = SiteViewPresenter(this)

    addSite.setOnClickListener {
      presenter.doAddorEditSite(siteTitle.text.toString(), siteDescription.text.toString())
    }

    siteCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
      if (isChecked)
        siteCheckBox.setEnabled(false)
      //set siteDateVisited to the Date the checkbox is clicked
    }

    addImage.setOnClickListener {
      presenter.doSelectImage()
    }

    setLocation.setOnClickListener{
      presenter.doShowMap()
    }

  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_site, menu)
    if (presenter.edit) {
      menu.getItem(0).setVisible(true)
    }
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> presenter.doCancel()
      R.id.item_delete -> presenter.doDelete()
    }
    return super.onOptionsItemSelected(item)
  }

  fun showSite(site: SiteModel) {
    siteTitle.setText(site.name)
    siteDescription.setText(site.description)
    siteImage.setImageBitmap(readImageFromPath(this,site.images))
    siteCheckBox.setChecked(site.visited)
    siteDateVisited.setText(site.date_visited)
    if(site.images != "") {
      addImage.setText(R.string.change_site_image)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }


  }

}
