package com.example.android.creatingtemplate


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.database.TemplatesDatabase

import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentCreatingTemplateBinding

/**
 * A simple [Fragment] subclass.
 */
class CreatingTemplateFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreatingTemplateBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_creating_template, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource =
            TemplatesDatabase.getInstance(application).templateDatabaseDao

        val viewModelFactory = CreatingTemplateViewModelFactory(dataSource,application)

        val creatingTemplateViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(CreatingTemplateViewModel::class.java)

        binding.creatingTemplateViewModel = creatingTemplateViewModel

        binding.completeButton.setOnClickListener { view:View ->
            creatingTemplateViewModel.createTemplate(binding.templateName.text.toString())
            view.findNavController().navigate(R.id.action_creatingTemplateFragment_to_trainingTemplatesListFragment)
        }

        return binding.root
    }


}
