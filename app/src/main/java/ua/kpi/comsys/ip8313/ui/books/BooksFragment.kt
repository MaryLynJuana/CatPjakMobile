package ua.kpi.comsys.ip8313.ui.books

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.gson.Gson
import org.json.JSONObject
import ua.kpi.comsys.ip8313.R
import ua.kpi.comsys.ip8313.databinding.FragmentBooksBinding
import ua.kpi.comsys.ip8313.databinding.FragmentHomeBinding
import java.io.IOException

class BooksFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    private fun getAssetData(filename: String): ByteArray? {
        val buffer: ByteArray
        try {
            val inputStream = requireContext().assets.open(filename)
            val size = inputStream.available()
            buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return buffer
    }

    private fun getAssetJsonData(filename: String): String? {
        val buffer = getAssetData(filename)
        return String(buffer!!)
    }

    private fun getBooks() : List<Book> {
        val data = getAssetJsonData("BooksList.txt")
        val booksJson = JSONObject(data).optString("books")
        return Gson().fromJson(booksJson, Array<Book>::class.java).toList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val books = getBooks()
        for (book in books) {
            println(book.title)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}