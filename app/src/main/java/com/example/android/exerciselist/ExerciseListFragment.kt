package com.example.android.exerciselist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.android.database.TemplatesDatabase

import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentExerciseListBinding
import com.example.android.util.TemporaryDataStorageClass

class ExerciseListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentExerciseListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_exercise_list, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = TemplatesDatabase.getInstance(application)
        val temporaryDataStorage = TemporaryDataStorageClass.instance

        val viewModelFactory = ExerciseListViewModelFactory(dataSource, application)

        val ExerciseListViewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(ExerciseListViewModel::class.java)

        val adapter = ExerciseAdapter()
        binding.exerciseList.adapter = adapter

        ExerciseListViewModel.getText()

        ExerciseListViewModel.temporaryExercises.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.exerciseListViewModel = ExerciseListViewModel

        binding.weekNumberAndDay.text = ExerciseListViewModel.weekDayAndNumber

        binding.addExerciseButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_exerciseListFragment_to_creatingExerciseFragment)
        }

        binding.completeButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_exerciseListFragment_to_trainingDaysListFragment)
        }

        return binding.root
    }


}