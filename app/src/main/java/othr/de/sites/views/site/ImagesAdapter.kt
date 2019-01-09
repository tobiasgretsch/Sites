package othr.de.sites.views.site

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_image.view.*
import othr.de.sites.R


interface ImageListener {
  fun onImageHold(image: String)
}

class ImagesAdapter constructor(
  private var images: List<String>,
  private var listener: ImageListener
) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.image)

    fun bind(image: String, listener: ImageListener) {
      Glide.with(itemView.context).load(image).into(itemView.image)
      itemView.setOnLongClickListener {
        listener.onImageHold(image)
        true
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_image, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int {
    return images.size
  }

  override fun onBindViewHolder(holder: ImagesAdapter.ViewHolder, position: Int) {
    holder.bind(images[position], listener)
  }

}
