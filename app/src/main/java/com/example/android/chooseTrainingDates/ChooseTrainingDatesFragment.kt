package com.example.android.chooseTrainingDates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentChooseTrainingDatesBinding

class ChooseTrainingDatesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentChooseTrainingDatesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_choose_training_dates, container, false
        )
        return binding.root
    }

}