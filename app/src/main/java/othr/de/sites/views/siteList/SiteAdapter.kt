package othr.de.sites.views.siteList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
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
) : androidx.recyclerview.widget.RecyclerView.Adapter<SiteAdapter.MainHolder>() {

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

  class MainHolder constructor(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    fun bind(site: SiteModel, listener: SiteListener) {
      Glide.with(itemView.context).load(site.images).into(itemView.listIcon)
      itemView.siteLat.text = "%.4f".format(site.latitude)
      itemView.siteLng.text = "%.4f".format(site.longitude)
      itemView.siteListTitle.text = site.name
      itemView.site_checkBox.isChecked = site.visited
      itemView.setOnClickListener { listener.onSiteClick(site) }
    }
  }
}