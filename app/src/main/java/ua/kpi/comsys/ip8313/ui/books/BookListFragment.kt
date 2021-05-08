package ua.kpi.comsys.ip8313.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ua.kpi.comsys.ip8313.databinding.FragmentBooksBinding


class BookListFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookViewModel

    private lateinit var adapter: BookAdapter

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
        adapter = BookAdapter(viewModel.bookList.value?.toMutableList() ?: mutableListOf())
        adapter.onClicked = {
            viewModel.loadBookData(it.isbn13)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        bookSearch.setQuery(viewModel.searchQuery, false)
        bookSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQuery = binding.bookSearch.query.toString()
                if (newText.isNullOrBlank() || newText.length < 3) {
                    adapter.update(mutableListOf())
                    return false
                }
                val specialChars = Regex("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")
                if (newText.contains(specialChars)) {
                    Toast.makeText(requireContext(), "Special characters not allowed", Toast.LENGTH_LONG).show()
                    return false
                }
                viewModel.loadSearchedBooks(newText)
                return true
            }
        })
        viewModel.bookList.observe(viewLifecycleOwner, Observer {
            adapter.update(it.toMutableList())
            if (it.isEmpty()) Toast.makeText(requireContext(), "No items found", Toast.LENGTH_LONG).show()
        })
        viewModel.currentBook.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            (parentFragment as BookContainerFragment).showBookItem()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}