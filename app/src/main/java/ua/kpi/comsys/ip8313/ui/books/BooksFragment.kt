package ua.kpi.comsys.ip8313.ui.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import org.json.JSONObject
import ua.kpi.comsys.ip8313.databinding.FragmentBooksBinding
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
        val bookList = getBooks()
        val adapter = BookAdapter(bookList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}