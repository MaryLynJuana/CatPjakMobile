package ua.kpi.comsys.ip8313.ui.pictures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ua.kpi.comsys.ip8313.R

class PictureAdapter(private var pictureList: MutableList<Picture>) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    inner class PictureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val pictureImgView = view.findViewById<ImageView>(R.id.picture_view)
        fun bindViewContent(pictureUrl: String) {
            Picasso.get()
                .load(pictureUrl)
                .placeholder(R.drawable.progress_animated)
                .error(R.drawable.ic_book)
                .into(pictureImgView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PictureViewHolder {
        val pictureView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.picture_item, viewGroup, false)
        return PictureViewHolder(pictureView)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bindViewContent(pictureList[position].webformatURL)
    }

    override fun getItemCount() = pictureList.size

    fun update(newPictureList: MutableList<Picture>) {
        this.pictureList = newPictureList
        notifyDataSetChanged()
    }
}