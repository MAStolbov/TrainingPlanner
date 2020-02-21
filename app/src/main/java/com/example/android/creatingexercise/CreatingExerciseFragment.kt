package com.example.android.creatingexercise


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
import com.example.android.trainingplanner.databinding.FragmentCreatingExerciseBinding


class CreatingExerciseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreatingExerciseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_creating_exercise, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = TemplatesDatabase.getInstance(application)

        val viewModelFactory = CreatingExerciseViewModelFactory(dataSource,application)

        val creatingExerciseViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(CreatingExerciseViewModel::class.java)

        creatingExerciseViewModel.getDayAndWeekNumberText()

        binding.creatingExerciseViewModel = creatingExerciseViewModel


        binding.weekNAndDayName.text = creatingExerciseViewModel.textWithDayAndNumberOfWeek

        binding.doneButton.setOnClickListener { view:View ->
            creatingExerciseViewModel.getExerciseInfo(binding.exrciseNamePlainText.text.toString(),
                binding.setsPlainText.text.toString(),binding.repsPlainText.text.toString(),
                binding.weightPlainText.text.toString())
            creatingExerciseViewModel.createTemporaryExercise()
            creatingExerciseViewModel.createNewExercise()
            view.findNavController().navigate(R.id.action_creatingExerciseFragment_to_creatingTrainingDayFragment)
        }

        return binding.root
    }


}
