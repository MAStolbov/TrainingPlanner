package com.example.android.trainingTemplateRedaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.android.database.TemplatesDatabase
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentRedactionBinding
import com.example.android.util.Util
import kotlinx.android.synthetic.main.fragment_redaction.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RedactionFragment : Fragment() {

    private var daysButtonMap = mutableMapOf<Int, Button>()
    private var daysImageMap = mutableMapOf<Int, ImageView>()
    private var addWeekImagesMap = mutableMapOf<Int, ImageView>()
    private var deleteWeekImagesMap = mutableMapOf<Int, ImageView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRedactionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_redaction, container, false)

        val mainScope = CoroutineScope(Dispatchers.Main)

        val application = requireNotNull(this.activity).application
        val dataSource = TemplatesDatabase.getInstance(application)

        val viewModelFactory = RedactionViewModelFactory(dataSource, application)

        val redactionViewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(RedactionViewModel::class.java)

        binding.redactionViewModel = redactionViewModel

        initMapOfViewForWeek(binding)

        redactionViewModel.templateId = RedactionFragmentArgs.fromBundle(arguments!!).templateID

        if (Util.redaction) {
            redactionViewModel.startDataLoading()
        }

        showOnDownloadView(binding)

        setClickListenerForImages(redactionViewModel)
        setClickListenerFortNameTextView(binding, redactionViewModel)
        setClickListenerForOkNameButton(binding, redactionViewModel)
        setClickListenerForDescriptionText(binding, redactionViewModel)
        setClickListenerForOkDescriptionButton(binding, redactionViewModel)
        setClickListenerForAddWeekImages(binding, redactionViewModel)
        setClickListenerForDeleteWeekImages(binding, redactionViewModel)

        binding.completeRedactionButton.setOnClickListener {
            redactionViewModel.clearData()
            redactionViewModel.updateData()
            findNavController().navigate(R.id.action_redactionFragment_to_trainingTemplatesListFragment)
        }

        Util.endLoading.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                showOffDownloadView()
                redactionViewModel.setTextForScreen()
                setTemplateNameAndDescriptionText(binding, redactionViewModel)
                showOnRequiredViews()
                showWeeksViews(redactionViewModel)
                showOffDaysImages(redactionViewModel)
                showDaysButtons(redactionViewModel)
            }
        })


        redactionViewModel.imageNumber.observe(viewLifecycleOwner, Observer {
            changeButtonVisibility(redactionViewModel.imageNumber.value)
        })
        return binding.root
    }

    private fun initMapOfViewForWeek(binding: FragmentRedactionBinding) {
        daysButtonMap = mutableMapOf(
            11 to binding.rFirstWeekMoButton,
            12 to binding.rFirstWeekTuButton,
            13 to binding.rFirstWeekWeButton,
            14 to binding.rFirstWeekThButton,
            15 to binding.rFirstWeekFrButton,
            16 to binding.rFirstWeekStButton,
            17 to binding.rFirstWeekSuButton,
            21 to binding.rSecondWeekMoButton,
            22 to binding.rSecondWeekTuButton,
            23 to binding.rSecondWeekWeButton,
            24 to binding.rSecondWeekThButton,
            25 to binding.rSecondWeekFrButton,
            26 to binding.rSecondWeekStButton,
            27 to binding.rSecondWeekSuButton,
            31 to binding.rThirdWeekMoButton,
            32 to binding.rThirdWeekTuButton,
            33 to binding.rThirdWeekWeButton,
            34 to binding.rThirdWeekThButton,
            35 to binding.rThirdWeekFrButton,
            36 to binding.rThirdWeekStButton,
            37 to binding.rThirdWeekSuButton,
            41 to binding.rFourthWeekMoButton,
            42 to binding.rFourthWeekTuButton,
            43 to binding.rFourthWeekWeButton,
            44 to binding.rFourthWeekThButton,
            45 to binding.rFourthWeekFrButton,
            46 to binding.rFourthWeekStButton,
            47 to binding.rFourthWeekSuButton
        )

        daysImageMap = mutableMapOf(
            11 to binding.rFirstWeekMoImage,
            12 to binding.rFirstWeekTuImage,
            13 to binding.rFirstWeekWeImage,
            14 to binding.rFirstWeekThImage,
            15 to binding.rFirstWeekFrImage,
            16 to binding.rFirstWeekStImage,
            17 to binding.rFirstWeekSuImage,
            21 to binding.rSecondWeekMoImage,
            22 to binding.rSecondWeekTuImage,
            23 to binding.rSecondWeekWeImage,
            24 to binding.rSecondWeekThImage,
            25 to binding.rSecondWeekFrImage,
            26 to binding.rSecondWeekStImage,
            27 to binding.rSecondWeekSuImage,
            31 to binding.rThirdWeekMoImage,
            32 to binding.rThirdWeekTuImage,
            33 to binding.rThirdWeekWeImage,
            34 to binding.rThirdWeekThImage,
            35 to binding.rThirdWeekFrImage,
            36 to binding.rThirdWeekStImage,
            37 to binding.rThirdWeekSuImage,
            41 to binding.rFourthWeekMoImage,
            42 to binding.rFourthWeekTuImage,
            43 to binding.rFourthWeekWeImage,
            44 to binding.rFourthWeekThImage,
            45 to binding.rFourthWeekFrImage,
            46 to binding.rFourthWeekStImage,
            47 to binding.rFourthWeekSuImage
        )

        addWeekImagesMap = mutableMapOf(
            1 to binding.addFirstWeekImage,
            2 to binding.addSecondWeekImage,
            3 to binding.addThirdWeekImage,
            4 to binding.addFourthWeekImage
        )

        deleteWeekImagesMap = mutableMapOf(
            1 to binding.deleteFirstWeekImage,
            2 to binding.deleteSecondWeekImage,
            3 to binding.deleteThirdWeekImage,
            4 to binding.deleteFourthWeekImage
        )
    }

    private fun setTemplateNameAndDescriptionText(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        binding.tNameText.text = viewModel.templateName
        binding.tDescriptonText.text = viewModel.templateDescription
    }


    private fun setClickListenerForDescriptionText(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        binding.tDescriptonText.setOnClickListener {
            binding.tDescriptonText.visibility = GONE
            binding.tDesriptionRedaction.setText(viewModel.templateDescription)
            binding.tDesriptionRedaction.visibility = VISIBLE
            binding.okDescriptionButton.visibility = VISIBLE
        }
    }


    private fun setClickListenerForOkDescriptionButton(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        binding.okDescriptionButton.setOnClickListener {
            viewModel.setNewTrainingTemplateDescription(binding.tDesriptionRedaction.text.toString())
            binding.tDescriptonText.text = viewModel.templateDescription
            binding.tDescriptonText.visibility = VISIBLE
            binding.tDesriptionRedaction.visibility = GONE
            binding.okDescriptionButton.visibility = GONE
        }
    }

    private fun setClickListenerForOkNameButton(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        binding.okNameButton.setOnClickListener {
            viewModel.setNewTrainingTemplateName(binding.tNameForRedaction.text.toString())
            binding.tNameText.text = viewModel.templateName
            binding.tNameText.visibility = VISIBLE
            binding.tNameForRedaction.visibility = GONE
            binding.okNameButton.visibility = GONE
        }
    }

    private fun setClickListenerFortNameTextView(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        binding.tNameText.setOnClickListener {
            binding.tNameText.visibility = GONE
            binding.tNameForRedaction.visibility = VISIBLE
            binding.tNameForRedaction.setText(viewModel.templateName)
            binding.okNameButton.visibility = VISIBLE
        }
    }

    private fun setClickListenerForImages(viewModel: RedactionViewModel) {
        daysImageMap.forEach { (key, value) ->
            value.setOnClickListener {
                viewModel.changeButtonVisibility(key)
            }
        }
    }

    private fun changeButtonVisibility(key: Int?) {
        daysButtonMap[key]?.visibility = VISIBLE
        daysImageMap[key]?.visibility = GONE
    }

    private fun showOnRequiredViews() {
        completeRedactionButton.visibility = VISIBLE
        tDescriptonText.visibility = VISIBLE
        hintDescriptonTextView.visibility = VISIBLE
        tAddWeekButton.visibility = VISIBLE
    }

    private fun showOnDownloadView(binding: FragmentRedactionBinding) {
        binding.downloadProgres.visibility = VISIBLE
        binding.downloadText.visibility = VISIBLE
    }

    private fun showOffDownloadView() {
        downloadProgres.visibility = GONE
        downloadText.visibility = GONE
    }

    private fun showWeeksViews(viewModel: RedactionViewModel) {
        firstWeekRedaction.isVisible = viewModel.showingWeek(1)
        firstWeekTextView.isVisible = viewModel.showingWeek(1)
        deleteFirstWeekImage.isVisible = viewModel.showingWeek(1)

        secondWeekRedaction.isVisible = viewModel.showingWeek(2)
        secondWeekTextView.isVisible = viewModel.showingWeek(2)
        deleteSecondWeekImage.isVisible = viewModel.showingWeek(2)

        thirdWeekRedction.isVisible = viewModel.showingWeek(3)
        thirdWeekTextView.isVisible = viewModel.showingWeek(3)
        deleteThirdWeekImage.isVisible = viewModel.showingWeek(3)

        fourthWeekRedaction.isVisible = viewModel.showingWeek(4)
        fourthWeekTextView.isVisible = viewModel.showingWeek(4)
        deleteFourthWeekImage.isVisible = viewModel.showingWeek(4)
    }

//    private fun showDaysButtons(viewModel: RedactionViewModel) {
//        buttonMap.forEach { (key, value) ->
//            if (key in 11..17) {
//                value.isVisible = viewModel.checkExistDays(key / 10, key - 10)
//            }
//            if (key in 21..27) {
//                value.isVisible = viewModel.checkExistDays(key / 10, key - 20)
//            }
//            if (key in 31..37) {
//                value.isVisible = viewModel.checkExistDays(key / 10, key - 30)
//            }
//            if (key in 41..47) {
//                value.isVisible = viewModel.checkExistDays(key / 10, key - 40)
//            }
//        }
//    }


    private fun setClickListenerForAddWeekImages(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        addWeekImagesMap.forEach { (key, value) ->
            value.setOnClickListener {
                viewModel.addNewWeek(key)
                showOnWeeks(key, binding)
            }
        }
    }

    private fun setClickListenerForDeleteWeekImages(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        deleteWeekImagesMap.forEach { (key, value) ->
            value.setOnClickListener {
                viewModel.deleteWeek(key)
                showOffWeeks(key, binding)
            }
        }
    }

    private fun showOnWeeks(weekNumber: Int, binding: FragmentRedactionBinding) {
        when (weekNumber) {
            1 -> {
                binding.addFirstWeekImage.visibility = GONE
                binding.addSecondWeekImage.isVisible = GONE == binding.secondWeekRedaction.visibility
                binding.firstWeekRedaction.visibility = VISIBLE
                binding.firstWeekTextView.visibility = VISIBLE
                binding.deleteFirstWeekImage.visibility = VISIBLE
            }
            2 -> {
                binding.addSecondWeekImage.visibility = GONE
                binding.addThirdWeekImage.isVisible = GONE == binding.thirdWeekTextView.visibility
                binding.secondWeekRedaction.visibility = VISIBLE
                binding.secondWeekTextView.visibility = VISIBLE
                binding.deleteSecondWeekImage.visibility = VISIBLE
            }
            3 -> {
                binding.addThirdWeekImage.visibility = GONE
                binding.addFourthWeekImage.isVisible = GONE == binding.fourthWeekRedaction.visibility
                binding.thirdWeekRedction.visibility = VISIBLE
                binding.thirdWeekTextView.visibility = VISIBLE
                binding.deleteThirdWeekImage.visibility = VISIBLE
            }
            4 -> {
                binding.addFourthWeekImage.visibility = GONE
                binding.fourthWeekRedaction.visibility = VISIBLE
                binding.fourthWeekTextView.visibility = VISIBLE
                binding.deleteFourthWeekImage.visibility = VISIBLE
            }
        }
    }

    private fun showOffWeeks(weekNumber: Int, binding: FragmentRedactionBinding) {
        when (weekNumber) {
            1 -> {
                binding.firstWeekRedaction.visibility = GONE
                binding.firstWeekTextView.visibility = GONE
                binding.deleteFirstWeekImage.visibility = GONE
            }
            2 -> {
                binding.secondWeekRedaction.visibility = GONE
                binding.secondWeekTextView.visibility = GONE
                binding.deleteSecondWeekImage.visibility = GONE
            }
            3 -> {
                binding.thirdWeekRedction.visibility = GONE
                binding.thirdWeekTextView.visibility = GONE
                binding.deleteThirdWeekImage.visibility = GONE
            }
            4 -> {
                binding.fourthWeekRedaction.visibility = GONE
                binding.fourthWeekTextView.visibility = GONE
                binding.deleteFourthWeekImage.visibility = GONE
            }
        }
    }

    private fun showDaysButtons(viewModel: RedactionViewModel) {
        daysButtonMap.forEach { (key, value) ->
            value.isVisible =
                viewModel.checkExistDays(Util.getWeekNumber(key), Util.getDayNumber(key))
        }
    }

    private fun showOffDaysImages(viewModel: RedactionViewModel) {
        daysImageMap.forEach { (key, value) ->
            value.isVisible =
                !viewModel.checkExistDays(Util.getWeekNumber(key), Util.getDayNumber(key))
        }
    }

}