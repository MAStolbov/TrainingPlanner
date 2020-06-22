package com.example.android.trainigtemplatelist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.database.TemplatesDatabase
import com.example.android.repository.Repository
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentTrainigTemplatesListBinding
import com.example.android.util.Util

class TrainingTemplatesListFragment : Fragment() {

    private val trainingTemplatesListViewModel:TrainingTemplatesListViewModel by viewModels {
        TrainingTemplatesListViewModelFactory(Repository.getRepositoryInstance(requireContext()))
    }

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
            trainingTemplatesListViewModel.deleteTemplate(binding.templateID.text.toString().toLong())
        }

        return binding.root
    }
}
