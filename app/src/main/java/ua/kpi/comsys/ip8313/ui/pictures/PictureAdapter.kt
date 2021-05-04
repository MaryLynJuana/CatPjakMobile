package ua.kpi.comsys.ip8313.ui.pictures

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8313.R

class PictureAdapter(private var pictureList: MutableList<Uri>) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    var onClicked: (Uri) -> Unit = {}
    inner class PictureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pictureView = view.findViewById<ImageView>(R.id.picture_view)
        fun bindViewContent(picture: Uri, onClicked: (Uri) -> Unit) {
            pictureView.setImageURI(picture)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PictureViewHolder {
        val pictureView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.picture_item, viewGroup, false)
        return PictureViewHolder(pictureView)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bindViewContent(pictureList[position], onClicked)
    }

    override fun getItemCount() = pictureList.size
}