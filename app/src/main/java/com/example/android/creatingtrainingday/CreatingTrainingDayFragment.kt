package com.example.android.creatingtrainingday


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.android.database.TemplatesDatabase

import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentCreatingTrainingDayBinding

/**
 * A simple [Fragment] subclass.
 */
class CreatingTrainingDayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentCreatingTrainingDayBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_creating_training_day,container,false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = TemplatesDatabase.getInstance(application)

        val viewModelFactory = CreatingTrainingDayViewModelFactory(dataSource,application)

        val creatingTrainingDayViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(CreatingTrainingDayViewModel::class.java)

        binding.creatingDayViewModel = creatingTrainingDayViewModel

        return binding.root
    }


}
