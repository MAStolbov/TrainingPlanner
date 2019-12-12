package com.example.android.creatingtemplate


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.database.TemplatesDatabase
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentCreatingTemplateBinding


/**
 * A simple [Fragment] subclass.
 */
class CreatingTemplateFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCreatingTemplateBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_creating_template, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = TemplatesDatabase.getInstance(application)

        val viewModelFactory = CreatingTemplateViewModelFactory(dataSource,application)

        val creatingTemplateViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(CreatingTemplateViewModel::class.java)

        binding.creatingTemplateViewModel = creatingTemplateViewModel

        binding.nextButton.setOnClickListener { view:View ->
            creatingTemplateViewModel.createTemplate(binding.templateNameEditText.text.toString(),binding.templateDescription.text.toString())
            creatingTemplateViewModel.sendNumberOfWeeks()
            creatingTemplateViewModel.sendSelectedTrainingDays()
            view.findNavController().navigate(R.id.action_creatingTemplateFragment_to_trainingDaysListFragment)
        }

        binding.addWeekButton.setOnClickListener{ view:View ->
            creatingTemplateViewModel.addWeek()
        }

        creatingTemplateViewModel.addWeek.observe(this, Observer {
            when (it){
                1 -> binding.firstWeekCheckBox.visibility = VISIBLE
                2 -> binding.secondWeekCheckBox.visibility = VISIBLE
                3 -> binding.thirdWeekCheckBox.visibility = VISIBLE
                4 -> binding.fourthWeekCheckBox.visibility = VISIBLE
            }
        })


        creatingTemplateViewModel.addWeek.observe(this, Observer {
            if(it > 4){
                val toast = Toast.makeText(
                    activity?.getApplicationContext(),
                    "Max Weeks Add!", Toast.LENGTH_SHORT
                )
                toast.show()
            }
        })

        return binding.root
    }

}
