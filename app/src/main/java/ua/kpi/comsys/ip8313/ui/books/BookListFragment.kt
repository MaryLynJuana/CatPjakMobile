package ua.kpi.comsys.ip8313.ui.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.core.content.ContentProviderCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import org.json.JSONObject
import ua.kpi.comsys.ip8313.AssetsManager
import ua.kpi.comsys.ip8313.R
import ua.kpi.comsys.ip8313.databinding.FragmentBooksBinding

class BookListFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookViewModel

    private lateinit var adapter: BookAdapter

    private val assetsManager by lazy { AssetsManager(requireContext()) }

    private fun getBooks() : MutableList<Book> {
        val data = assetsManager.getAssetData("BooksList.txt")
        val booksJson = JSONObject(String(data!!)).optString("books")
        return Gson().fromJson(booksJson, Array<Book>::class.java).toMutableList()
    }

    private fun getBookInfo(bookId: String) : Book? {
        val data = assetsManager.getAssetData("$bookId.txt") ?: return null
        return Gson().fromJson(String(data), Book::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.bookList.isEmpty()) viewModel.bookList.addAll(getBooks())
        adapter = BookAdapter(viewModel.bookList)
        adapter.onClicked = {
            viewModel.currentBook = getBookInfo(it.isbn13) ?: it
            (parentFragment as BookContainerFragment).showBookItem()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        addButton.setOnClickListener{ (parentFragment as BookContainerFragment).showBookCreationForm() }
        bookSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}