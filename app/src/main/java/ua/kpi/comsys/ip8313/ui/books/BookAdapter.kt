package ua.kpi.comsys.ip8313.ui.books

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8313.R

class BookAdapter(private val bookList: List<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookImageView  = view.findViewById<ImageView>(R.id.book_image)
        val bookTextView = view.findViewById<TextView>(R.id.book_description)

        fun bindViewContent(book: Book) {
            if (book.image.isBlank()) {
                bookImageView.setImageDrawable(
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_book)
                )
            }
            else {
                val inputStream = itemView.context.assets.open("Images/${book.image}")
                bookImageView.setImageDrawable(Drawable.createFromStream(inputStream, null))
                inputStream.close()
            }

            bookTextView.text = "${book.title}\n\n${book.subtitle}\n\n${book.price}"
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookViewHolder {
        val bookView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.book_row_item, viewGroup, false)
        return BookViewHolder(bookView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bindViewContent(bookList[position])
    }

    override fun getItemCount() = bookList.size
}