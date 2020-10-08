package com.example.android.chooseTrainingDates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.android.repository.Repository
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentChooseTrainingDatesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseTrainingDatesFragment : Fragment() {

    private val chooseTrainingDatesViewModel: ChooseTrainingDatesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentChooseTrainingDatesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_choose_training_dates, container, false
        )

        binding.calendarView.setOnClickListener {
            binding.calendarView.date
        }

        return binding.root
    }

}