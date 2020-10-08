package com.example.android.creatingexercise


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.android.repository.Repository
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentCreatingExerciseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreatingExerciseFragment : Fragment() {

    private val creatingExerciseViewModel: CreatingExerciseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreatingExerciseBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_creating_exercise, container, false
        )

        binding.creatingExerciseViewModel = creatingExerciseViewModel

        binding.weekNAndDayName.text = creatingExerciseViewModel.getDayAndWeekNumberText()

        binding.deleteExerciseButton.setOnClickListener {view:View ->
            creatingExerciseViewModel.deleteExercise()
            view.findNavController().navigate(R.id.action_creatingExerciseFragment_to_exerciseListFragment)
        }

        if (CreatingExerciseFragmentArgs.fromBundle(requireArguments()).process == "redaction") {
            creatingExerciseViewModel.getExercise(CreatingExerciseFragmentArgs.fromBundle(requireArguments()).localId)
            binding.exrciseNamePlainText.setText(creatingExerciseViewModel.exercise.exerciseName)
            binding.setsPlainText.setText(creatingExerciseViewModel.exercise.set)
            binding.repsPlainText.setText(creatingExerciseViewModel.exercise.rep)
            binding.weightPlainText.setText(creatingExerciseViewModel.exercise.weight)
        }

        binding.doneButton.setOnClickListener { view: View ->
            if (CreatingExerciseFragmentArgs.fromBundle(requireArguments()).process == "redaction") {
                creatingExerciseViewModel.updateExercise(
                    binding.exrciseNamePlainText.text.toString(),
                    binding.setsPlainText.text.toString(),
                    binding.repsPlainText.text.toString(),
                    binding.weightPlainText.text.toString()
                )
            } else {
                creatingExerciseViewModel.createNewExercise(
                    binding.exrciseNamePlainText.text.toString(),
                    binding.setsPlainText.text.toString(),
                    binding.repsPlainText.text.toString(),
                    binding.weightPlainText.text.toString()
                )
            }
            view.findNavController()
                .navigate(R.id.action_creatingExerciseFragment_to_exerciseListFragment)
        }

        return binding.root
    }


}
