package com.example.android.creatingtrainingday


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
import com.example.android.trainingplanner.databinding.FragmentCreatingTrainingDayBinding
import com.example.android.util.TemporaryDataStorageClass
import com.example.android.util.Util

/**
 * A simple [Fragment] subclass.
 */
class CreatingTrainingDayFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCreatingTrainingDayBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_creating_training_day, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TemplatesDatabase.getInstance(application)
        val temporaryDataStorage = TemporaryDataStorageClass.instance

        val viewModelFactory = CreatingTrainingDayViewModelFactory(dataSource, application)

        val creatingTrainingDayViewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(CreatingTrainingDayViewModel::class.java)

        val adapter = ExerciseAdapter()

        binding.exerciseList.adapter = adapter
        creatingTrainingDayViewModel.temporaryExercises.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.creatingDayViewModel = creatingTrainingDayViewModel


        if (Util.newDayCheck == false) {
            creatingTrainingDayViewModel.getWeekNumber()
            creatingTrainingDayViewModel.getDayOfTheWeek()
            creatingTrainingDayViewModel.getText()
            creatingTrainingDayViewModel.createNewTrainingDay()
            Util.newDayCheck = true
        }

        binding.weekNumberAndDay.text = creatingTrainingDayViewModel.weekDayAndNumber

        binding.addExerciseButton.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_creatingTrainingDayFragment_to_creatingExerciseFragment)
        }

        binding.completeButton.setOnClickListener { view: View ->
            dataSource.temporaryExerciseDao.clearExercise()
            temporaryDataStorage.putToDaysExercisesMap()
            Util.newDayCheck = false
            view.findNavController()
                .navigate(R.id.action_creatingTrainingDayFragment_to_trainingDaysListFragment)
        }

        return binding.root
    }


}
