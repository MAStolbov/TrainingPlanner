package com.example.android.creatingtrainingday


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

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

        return binding.root
    }


}
