package com.example.android.trainingTemplateRedaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.database.TemplatesDatabase
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentRedactionBinding
import com.example.android.util.TemporaryDataStorageClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RedactionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRedactionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_redaction,container,false)

        val temporaryDataStorage = TemporaryDataStorageClass.instance

        val mainScope = CoroutineScope(Dispatchers.Main)

        val application = requireNotNull(this.activity).application
        val dataSource = TemplatesDatabase.getInstance(application)

        val viewModelFactory = RedactionViewModelFactory(dataSource, application)

        val redactionViewModel = ViewModelProviders.of(this,viewModelFactory
        ).get(RedactionViewModel::class.java)

        redactionViewModel.templateId = RedactionFragmentArgs.fromBundle(arguments!!).templateID


        redactionViewModel.startDataLoading()

        redactionViewModel.endDataLoading.observe(viewLifecycleOwner, Observer {
            binding.downloadProgres.visibility = GONE
            binding.downloadText.visibility = GONE
            binding.templateInfo.text = redactionViewModel.setTextForScreen()
            binding.templateInfo.visibility = VISIBLE
        })


            return binding.root
    }

}