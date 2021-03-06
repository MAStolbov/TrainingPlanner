package com.example.android.trainingplanner


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false
        )

        binding.trainingTemplatesButton.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.action_titleFragment_to_trainingTemplatesListFragment)
        }

        return binding.root
    }

}
