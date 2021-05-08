package ua.kpi.comsys.ip8313.ui.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ua.kpi.comsys.ip8313.R

class BookAdapter(private var bookList: MutableList<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    var onClicked: (Book) -> Unit = {}
    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookImageView = view.findViewById<ImageView>(R.id.book_image)
        val bookTextView = view.findViewById<TextView>(R.id.book_description)

        fun bindViewContent(book: Book, onClicked: (Book) -> Unit) {
            if (book.image.isBlank()) {
                bookImageView.setImageDrawable(
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_book)
                )
            }
            else {
                Picasso.get()
                    .load(book.image)
                    .placeholder(R.drawable.progress_animated)
                    .error(R.drawable.ic_book)
                    .into(bookImageView)
            }

            bookTextView.text = "${book.title}\n\n${book.subtitle}\n\n${book.price}"
            itemView.setOnClickListener {
                onClicked(book)
            }
        }
    }

    fun update(newBookList: MutableList<Book>) {
        this.bookList = newBookList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BookViewHolder {
        val bookView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.book_row_item, viewGroup, false)
        return BookViewHolder(bookView)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bindViewContent(bookList[position], onClicked)
    }

    override fun getItemCount() = bookList.size
}