package ua.kpi.comsys.ip8313.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButtonToggleGroup
import ua.kpi.comsys.ip8313.R
import ua.kpi.comsys.ip8313.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        toggleGroup.addOnButtonCheckedListener {
                _: MaterialButtonToggleGroup, id: Int, isChecked: Boolean ->
                    when {
                        id == R.id.graphButton && isChecked -> {
                            graphView.isVisible = true
                            diagramView.isVisible = false
                        }
                        id == R.id.diagramButton && isChecked -> {
                            graphView.isVisible = false
                            diagramView.isVisible = true
                        }
                    }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}