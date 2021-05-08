package ua.kpi.comsys.ip8313.ui.books

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ua.kpi.comsys.ip8313.R
import ua.kpi.comsys.ip8313.databinding.FragmentBookItemBinding


class BookItemFragment : Fragment() {
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
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        val currentBook = viewModel.currentBook.value
        if (currentBook?.image.isNullOrBlank()) {
            bookImage.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_book)
            )
        }
        else {
            Picasso.get()
                .load(currentBook?.image)
                .placeholder(R.drawable.progress_animated)
                .error(R.drawable.ic_book)
                .into(bookImage)
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
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                viewModel.currentBook.postValue(null)
                (parentFragment as BookContainerFragment).showBookList()
            }
            return@setOnKeyListener true
        }
    }

}