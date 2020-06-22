package com.example.android.exerciselist


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

import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentExerciseListBinding

class ExerciseListFragment : Fragment() {

    private val exerciseListViewModel: ExerciseListViewModel by viewModels {
        ExerciseListViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentExerciseListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_exercise_list, container, false
        )

        val adapter = ExerciseAdapter()
        binding.exerciseList.adapter = adapter

        exerciseListViewModel.getText()

        exerciseListViewModel.temporaryExercises.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.exerciseListViewModel = exerciseListViewModel

        binding.weekNumberAndDay.text = exerciseListViewModel.getText()

        binding.addExerciseButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_exerciseListFragment_to_creatingExerciseFragment)
        }

        binding.completeButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                ExerciseListFragmentDirections.actionExerciseListFragmentToRedactionFragment(Procces = "Work in Progress")
            )
        }

        return binding.root
    }


}
