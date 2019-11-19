package com.example.android.trainigtemplate


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.database.TemplatesDatabase
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentTrainigTemplatesListBinding

class TrainingTemplatesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTrainigTemplatesListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_trainig_templates_list, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource =
           TemplatesDatabase.getInstance(application).templateDatabaseDao
        val viewModelFactory = TrainingTemplatesListViewModelFactory(dataSource, application)

        val adapter = TrainingTemplateAdapter()

        binding.templateList.adapter = adapter

        val trainingTemplatesListViewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(TrainingTemplatesListViewModel::class.java)

        binding.trainingTemplateViewModel = trainingTemplatesListViewModel

        trainingTemplatesListViewModel.navigateToCreatingTemplate.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(R.id.action_trainingTemplatesListFragment_to_creatingTemplateFragment)
                trainingTemplatesListViewModel.doneNavigation()
            }
        })

        return binding.root
    }
}
