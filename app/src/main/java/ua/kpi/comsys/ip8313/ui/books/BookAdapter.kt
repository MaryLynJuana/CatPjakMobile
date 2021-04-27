package ua.kpi.comsys.ip8313.ui.books

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ua.kpi.comsys.ip8313.R
import java.util.*
import kotlin.collections.ArrayList

class BookAdapter(private var bookList: MutableList<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>(), Filterable {
    var bookInitialList = bookList
    var onClicked: (Book) -> Unit = {}
    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookImageView = view.findViewById<ImageView>(R.id.book_image)
        val bookTextView = view.findViewById<TextView>(R.id.book_description)
        val bookDeleteButton = view.findViewById<Button>(R.id.book_delete_button)

        fun bindViewContent(book: Book, onClicked: (Book) -> Unit) {
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
            itemView.setOnClickListener {
                onClicked(book)
            }
            bookDeleteButton.setOnClickListener { removeItem(book) }
        }
    }

    fun removeItem(item: Book) {
        bookList.remove(item)
        bookInitialList.remove(item)
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

    fun filterBookList(searchQuery: String): ArrayList<Book> {
        val resultList = ArrayList<Book>()
        bookList.forEach {
            if (it.title.toLowerCase()
                    .contains(searchQuery.toLowerCase())
            ) {
                resultList.add(it)
            }
        }
        return resultList
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchQuery = constraint.toString()
                val filterResults = FilterResults()
                filterResults.values = if (searchQuery.isEmpty()) bookInitialList
                    else filterBookList(searchQuery)
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                bookList = results?.values as ArrayList<Book>
                notifyDataSetChanged()
            }
        }
    }
}