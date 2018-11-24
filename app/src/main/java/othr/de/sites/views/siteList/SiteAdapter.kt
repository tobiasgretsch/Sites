package othr.de.sites.views.siteList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_site.view.*
import othr.de.sites.R
import othr.de.sites.helpers.readImageFromPath
import othr.de.sites.models.SiteModel

interface SiteListener {
  fun onSiteClick(site: SiteModel)
}

class SiteAdapter constructor(
  private var sites: List<SiteModel>,
  private var listener: SiteListener
) : RecyclerView.Adapter<SiteAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_site, parent, false))
  }

  override fun getItemCount(): Int {
    return sites.size
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val site = sites[holder.adapterPosition]
    holder.bind(site, listener)
  }

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(site: SiteModel, listener: SiteListener) {
      itemView.listIcon.setImageBitmap(readImageFromPath(itemView.context, site.images))
      itemView.siteLat.text = site.latitute.toString()
      itemView.siteLng.text = site.longtitue.toString()
      itemView.siteListTitle.text = site.name
      itemView.site_checkBox.isChecked = site.visited
      itemView.setOnClickListener { listener.onSiteClick(site) }
    }
  }
}