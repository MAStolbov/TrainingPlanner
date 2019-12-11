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
import com.example.android.util.SelectedTrainingDays
import com.example.android.database.TemplatesDatabase

import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentTrainingDaysListBinding

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

        val trainingDaysListViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(TrainingDaysListViewModel::class.java)

        binding.trainingDaysListViewModel = trainingDaysListViewModel

        trainingDaysListViewModel.getSelectedTrainingDays()

        trainingDaysListViewModel.getTemplateName()
        binding.templateName.text = trainingDaysListViewModel.templateName

        trainingDaysListViewModel.showSelectedTrainingDays.observe(this, Observer {
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(1)){
                binding.firstWeekMondayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(2)){
                binding.firstWeekTuesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(3)){
                binding.firstWeekWednesdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(4)){
                binding.firstWeekThursdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(5)){
                binding.firstWeekFridayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(6)){
                binding.firstWeekSaturdayButton.visibility = VISIBLE
            }
            if (trainingDaysListViewModel.firstTrainingWeek.getValue(7)){
                binding.firstWeekSundayButton.visibility = VISIBLE
            }
        })


        binding.completeButton.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.action_trainingDaysListFragment_to_trainingTemplatesListFragment)
        }

        return binding.root
    }


}
