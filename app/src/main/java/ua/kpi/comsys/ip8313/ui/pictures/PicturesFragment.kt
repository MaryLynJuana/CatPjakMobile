package ua.kpi.comsys.ip8313.ui.pictures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ua.kpi.comsys.ip8313.databinding.FragmentPicturesBinding

class PicturesFragment : Fragment() {
    private var _binding: FragmentPicturesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PictureViewModel
    private lateinit var adapter: PictureAdapter
    private lateinit var db: PictureDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            db = PictureDatabase.getDatabase(requireContext())
        }
        viewModel = ViewModelProvider(requireActivity()).get(PictureViewModel::class.java)
        viewModel.repository = PictureRepository(PictureRemoteDataSource(getPicturesApi()), PictureLocalDataSource(db))
        _binding = FragmentPicturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PictureAdapter(viewModel.pictureList.value?.toMutableList()?: mutableListOf())
        picturesRecycler.adapter = adapter
        picturesRecycler.layoutManager = PicturesLayoutManager()
        viewModel.loadPictures("night+city")
        viewModel.pictureList.observe(viewLifecycleOwner, Observer {
            adapter.update(it.toMutableList())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}