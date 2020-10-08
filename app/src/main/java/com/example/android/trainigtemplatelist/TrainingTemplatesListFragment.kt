package com.example.android.trainigtemplatelist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentTrainigTemplatesListBinding

class TrainingTemplatesListFragment : Fragment() {

    private val trainingTemplatesListViewModel:TrainingTemplatesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTrainigTemplatesListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_trainig_templates_list, container, false
        )

        val adapter = TrainingTemplateAdapter()
        binding.templateList.adapter = adapter

        trainingTemplatesListViewModel.templates.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.trainingTemplateViewModel = trainingTemplatesListViewModel

        binding.createButton.setOnClickListener { view:View ->
            view.findNavController().navigate(TrainingTemplatesListFragmentDirections.actionTrainingTemplatesListFragmentToRedactionFragment(Procces = "Creation"))
        }

        binding.backButton.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.action_trainingTemplatesListFragment_to_titleFragment)
        }

        binding.deleteButton.setOnClickListener { view:View ->
            trainingTemplatesListViewModel.deleteTemplate()
        }

        return binding.root
    }
}
