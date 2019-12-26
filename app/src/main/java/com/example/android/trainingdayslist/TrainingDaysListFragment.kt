package com.example.android.trainingdayslist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.database.TemplatesDatabase

import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentTrainingDaysListBinding
import com.example.android.util.EntityStorage

/**
 * A simple [Fragment] subclass.
 */
class TrainingDaysListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTrainingDaysListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_training_days_list, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = TemplatesDatabase.getInstance(application)

        val viewModelFactory = TrainingDaysListViewModelFactory(dataSource, application)

        val trainingDaysListViewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(TrainingDaysListViewModel::class.java)

        binding.trainingDaysListViewModel = trainingDaysListViewModel

        /**
         * get information about created template
         */
        trainingDaysListViewModel.getNumberOfWeeks()
        trainingDaysListViewModel.getSelectedTrainingDays()
        trainingDaysListViewModel.getTemplateNameAndDescription()

        binding.templateName.text = trainingDaysListViewModel.templateName
        binding.templateDescription.text = trainingDaysListViewModel.templateDescription

        trainingDaysListViewModel.fillInDay.observe(this, Observer {
            if (it == true){
                findNavController().navigate(R.id.action_trainingDaysListFragment_to_creatingTrainingDayFragment)
            }
        })

        trainingDaysListViewModel.showWeeks.observe(this, Observer {

            if (trainingDaysListViewModel.numberOfWeeks in 1..4){
                binding.firstWeekButtons.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.numberOfWeeks in 2..4){
                binding.secondWeekButtons.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.numberOfWeeks in 3..4){
                binding.thirdWeekButtons.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.numberOfWeeks == 4){
                binding.fourthWeekButtons.visibility = VISIBLE
            }
        })

        trainingDaysListViewModel.showSelectedTrainingDays.observe(this, Observer {

            /**
             * days of the first week
             */
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(1)) {
                binding.firstWeekMondayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(2)) {
                binding.firstWeekTuesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(3)) {
                binding.firstWeekWednesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(4)) {
                binding.firstWeekThursdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(5)) {
                binding.firstWeekFridayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(6)) {
                binding.firstWeekSaturdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(7)) {
                binding.firstWeekSundayButton.visibility = VISIBLE
            }

            /**
             * days of the second week
             */
            if (trainingDaysListViewModel.secondTrainingWeek.getValue(1)) {
                binding.secondWeekMondayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.secondTrainingWeek.getValue(2)) {
                binding.secondWeekTuesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.secondTrainingWeek.getValue(3)) {
                binding.secondWeekWednesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.secondTrainingWeek.getValue(4)) {
                binding.secondWeekThursdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.secondTrainingWeek.getValue(5)) {
                binding.secondWeekFridayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.secondTrainingWeek.getValue(6)) {
                binding.secondWeekSaturdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.secondTrainingWeek.getValue(7)) {
                binding.secondWeekSundayButton.visibility = VISIBLE
            }

            /**
             * days of the third week
             */
            if (trainingDaysListViewModel.thirdTrainingWeek.getValue(1)) {
                binding.thirdWeekMondayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.thirdTrainingWeek.getValue(2)) {
                binding.thirdWeekTuesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.thirdTrainingWeek.getValue(3)) {
                binding.thirdWeekWednesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.thirdTrainingWeek.getValue(4)) {
                binding.thirdWeekThursdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.thirdTrainingWeek.getValue(5)) {
                binding.thirdWeekFridayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.thirdTrainingWeek.getValue(6)) {
                binding.thirdWeekSaturdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.thirdTrainingWeek.getValue(7)) {
                binding.thirdWeekSundayButton.visibility = VISIBLE
            }

            /**
             * days if the fourth week
             */
            if (trainingDaysListViewModel.fourthTrainingWeek.getValue(1)) {
                binding.fourthWeekMondayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.fourthTrainingWeek.getValue(2)) {
                binding.fourthWeekTuesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.fourthTrainingWeek.getValue(3)) {
                binding.fourthWeekWednesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.fourthTrainingWeek.getValue(4)) {
                binding.fourthWeekThursdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.fourthTrainingWeek.getValue(5)) {
                binding.fourthWeekFridayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.fourthTrainingWeek.getValue(6)) {
                binding.fourthWeekSaturdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.fourthTrainingWeek.getValue(7)) {
                binding.fourthWeekSundayButton.visibility = VISIBLE
            }
        })


        binding.completeButton.setOnClickListener { view: View ->
            trainingDaysListViewModel.putEntitysInDatabase()
            EntityStorage.clearAllMaps()
            view.findNavController()
                .navigate(R.id.action_trainingDaysListFragment_to_trainingTemplatesListFragment)
        }

        return binding.root
    }


}
