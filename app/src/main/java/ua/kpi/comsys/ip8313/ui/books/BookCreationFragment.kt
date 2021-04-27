package ua.kpi.comsys.ip8313.ui.books

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.kpi.comsys.ip8313.databinding.FragmentBookCreationBinding

class BookCreationFragment: Fragment() {
    private var _binding: FragmentBookCreationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BookViewModel

    private fun createBookItem() = with(binding) {
        val edits = listOf<EditText>(titleEdit, subtitleEdit, priceEdit)
        edits.forEach {
            if (it.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "You should specify book's title, subtitle and price",
                    Toast.LENGTH_SHORT
                ).show()
                return@with
            }
        }
        val title = titleEdit.text.toString()
        val subtitle = subtitleEdit.text.toString()
        val price = priceEdit.text.toString()

        val isbnPrev = viewModel.bookList.last().isbn13
        val newBook = Book(title, subtitle, isbnPrev + 1, price, "")
        viewModel.bookList.add(0, newBook)
        (parentFragment as BookContainerFragment).showBookList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        _binding = FragmentBookCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bookCreationButton.setOnClickListener { createBookItem() }
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) (parentFragment as BookContainerFragment).showBookList()
            return@setOnKeyListener true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}