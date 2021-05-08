package ua.kpi.comsys.ip8313.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ua.kpi.comsys.ip8313.R

class BookContainerFragment: Fragment() {
    fun showBookList() {
        childFragmentManager.commit {
            replace(R.id.book_container, BookListFragment())
        }
    }
    fun showBookItem() {
        childFragmentManager.commit {
            replace(R.id.book_container, BookItemFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBookList()
    }
}