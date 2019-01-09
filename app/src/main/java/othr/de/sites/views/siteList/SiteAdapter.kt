package othr.de.sites.views.siteList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_site.view.*
import othr.de.sites.R
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
      itemView.siteDescription.text = site.description
      itemView.siteLocation.text = String.format("(%.4f,%.4f)", site.latitude, site.longitude)
      itemView.siteListTitle.text = site.name
      itemView.site_checkBox.isChecked = site.visited
      if (site.favorite) {
        itemView.favorite_checkBox.visibility = View.VISIBLE
        itemView.favorite_checkBox.isChecked = site.favorite
      } else {
        itemView.favorite_checkBox.visibility = View.INVISIBLE
      }
      itemView.setOnClickListener { listener.onSiteClick(site) }

      if (site.images.size > 0) Glide.with(itemView.context).load(site.images[0]).into(itemView.listIcon)
    }
  }
}