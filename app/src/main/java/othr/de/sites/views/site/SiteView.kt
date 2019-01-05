package othr.de.sites.views.site

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import othr.de.sites.R

import kotlinx.android.synthetic.main.activity_site_view.*
import kotlinx.android.synthetic.main.content_site_view.*
import othr.de.sites.models.SiteModel
import othr.de.sites.views.base.BaseView

class SiteView : BaseView(), ImageListener {

  private lateinit var presenter: SitePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_site_view)

    super.init(siteToolbar, true)

    presenter = initPresenter(SitePresenter(this)) as SitePresenter

    recyclerViewImages.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)

    siteCheckBox.setOnClickListener {
      presenter.doChangeCheckBox()
    }

    favoritesCheckBox.setOnClickListener {
      presenter.doChangeFavoriteCheckBox()
    }

    addImage.setOnClickListener {
      presenter.doSelectImage()
    }

    setLocation.setOnClickListener {
      presenter.doShowMap()
    }

  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_site, menu)
    if (presenter.edit) {
      menu.getItem(1).isVisible = true
      menu.getItem(2).setTitle(R.string.menu_saveSite)
    }
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_save -> presenter.doAddorEditSite(
        siteTitle.text.toString(),
        siteDescription.text.toString(),
        siteAdditionalInfo.text.toString(),
        ratingBar.rating
      )
      R.id.item_delete -> presenter.doDelete()
      R.id.item_share -> presenter.doShareSite()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun showSite(site: SiteModel) {
    siteTitle.setText(site.name)
    siteDescription.setText(site.description)
    recyclerViewImages.adapter = ImagesAdapter(site.images, this)
    recyclerViewImages.adapter?.notifyDataSetChanged()
    //Glide.with(this).load(site.images).into(siteImage)
    //siteImage.setImageBitmap(readImageFromPath(this, site.images))
    siteCheckBox.isChecked = site.visited
    siteDateVisited.text = site.date_visited
    siteAdditionalInfo.setText(site.additionalInfo)
    ratingBar.rating = site.rating
    favoritesCheckBox.isChecked = site.favorite
    if (site.images.size >= 4) {
      addImage.isEnabled = false
      addImage.setText(R.string.site_changeImage)
    } else {
      addImage.isEnabled = true
      addImage.setText(R.string.site_add_Image)
    }
  }

  override fun onImageHold(image: String) {
    presenter.doDeleteImage(image)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }

  }

  //override fun onResume() {
  //  super.onResume()
  //  presenter.doRestartLocationUpdates()
  //}

  override fun onBackPressed() {
    presenter.doCancel()
    super.onBackPressed()
  }
}
