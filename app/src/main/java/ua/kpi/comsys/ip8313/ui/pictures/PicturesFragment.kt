package ua.kpi.comsys.ip8313.ui.pictures

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.kpi.comsys.ip8313.databinding.FragmentPicturesBinding

class PicturesFragment : Fragment() {
    private var _binding: FragmentPicturesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PictureViewModel
    private lateinit var adapter: PictureAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(PictureViewModel::class.java)
        _binding = FragmentPicturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PictureAdapter(viewModel.pictureList)
        picturesRecycler.adapter = adapter
        picturesRecycler.layoutManager = PicturesLayoutManager()
        addButton.setOnClickListener { getPictureFromGallery() }
    }

    companion object {
        private const val GET_IMAGE = 1
    }

    private fun getPictureFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GET_IMAGE)
    }

    private fun showPicture(picture: Uri) {
        viewModel.pictureList.add(picture)
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GET_IMAGE){
            data?.data?.let(this::showPicture)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}