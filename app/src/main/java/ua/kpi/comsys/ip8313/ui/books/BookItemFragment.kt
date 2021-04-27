package ua.kpi.comsys.ip8313.ui.books

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.kpi.comsys.ip8313.R
import ua.kpi.comsys.ip8313.databinding.FragmentBookItemBinding


class BookItemFragment() : Fragment() {
    private var _binding: FragmentBookItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)
        _binding = FragmentBookItemBinding.inflate(inflater, container, false)
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action === KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    (parentFragment as BookContainerFragment).showBookList()
                    return@OnKeyListener true
                }
            }
            false
        })
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        val currentBook = viewModel.currentBook
        if (currentBook?.image.isNullOrBlank()) {
            bookImage.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_book)
            )
        }
        else {
            val inputStream = requireContext().assets.open("Images/${currentBook?.image}")
            bookImage.setImageDrawable(Drawable.createFromStream(inputStream, null))
            inputStream.close()
        }
        bookTitle.text ="Title: ${currentBook?.title}"
        bookSubtitle.text = "Subtitle: ${currentBook?.subtitle}"
        bookPrice.text = "Price: ${currentBook?.price}$"
        bookDescription.text = if (currentBook?.desc.isNullOrBlank()) "" else currentBook?.desc
        bookAuthors.text = "Authors: " + if (currentBook?.authors.isNullOrBlank()) "no info" else currentBook?.authors
        bookPublisher.text = "Publisher: " + if (currentBook?.publisher.isNullOrBlank()) "no info" else currentBook?.publisher
        bookPages.text = "Pages: " + if (currentBook?.pages.isNullOrBlank()) "no info" else currentBook?.pages
        bookYear.text = "Year: " + if (currentBook?.year.isNullOrBlank()) "no info" else currentBook?.year
        bookRating.text = "Rating: " + if (currentBook?.rating.isNullOrBlank()) "no info" else "${currentBook?.rating}/5"

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) (parentFragment as BookContainerFragment).showBookList()
            return@setOnKeyListener true
        }
    }

}