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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.repository.Repository
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.FragmentRedactionBinding
import com.example.android.util.Util

class RedactionFragment : Fragment() {

    private val redactionViewModel: RedactionViewModel by viewModels {
        RedactionViewModelFactory(Repository.getRepositoryInstance(requireContext()))
    }
    private var daysButtonMap = mutableMapOf<Int, Button>()
    private var daysImageMap = mutableMapOf<Int, ImageView>()
    private var addWeekImagesMap = mutableMapOf<Int, ImageView>()
    private var deleteWeekImagesMap = mutableMapOf<Int, ImageView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRedactionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_redaction, container, false)

        binding.redactionViewModel = redactionViewModel
        initMapOfViewForWeek(binding)

        when (RedactionFragmentArgs.fromBundle(requireArguments()).Procces) {
            "Creation" -> {
                showViewsForCreation(binding, redactionViewModel)
                redactionViewModel.createTrainingTemplate()
            }
            "Redaction" -> {
                redactionViewModel.templateId =
                    RedactionFragmentArgs.fromBundle(requireArguments()).templateID
                redactionViewModel.startDataLoading()
                changeDownloadViewsVisibility(binding, VISIBLE)
            }
            "Work in Progress" -> callShowingViewsFunction(binding, redactionViewModel)
            "Delete" -> {
                redactionViewModel.templateId =
                    RedactionFragmentArgs.fromBundle(requireArguments()).templateID
                redactionViewModel.templateName =
                    RedactionFragmentArgs.fromBundle(requireArguments()).templateName
                showViewsForDeleting(binding, redactionViewModel)
            }
        }


        setClickListenerForDaysImages()
        setClickListenerFortNameTextView(binding, redactionViewModel)
        setClickListenerForOkNameButton(binding, redactionViewModel)
        setClickListenerForDescriptionText(binding, redactionViewModel)
        setClickListenerForOkDescriptionButton(binding, redactionViewModel)
        setClickListenerForAddWeekImages(binding, redactionViewModel)
        setClickListenerForDeleteWeekImages(binding, redactionViewModel)
        setClickListenerForDaysButtons(redactionViewModel)

        binding.completeRedactionButton.setOnClickListener {
            redactionViewModel.saveData()
            findNavController().navigate(R.id.action_redactionFragment_to_trainingTemplatesListFragment)
        }

        binding.rDeleteTemplateButton.setOnClickListener {
            redactionViewModel.deleteTemplate()
            findNavController().navigate(R.id.action_redactionFragment_to_trainingTemplatesListFragment)
        }

        binding.rCancleButton.setOnClickListener {
            findNavController().navigate(R.id.action_redactionFragment_to_trainingTemplatesListFragment)
        }

        Util.endLoading.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                changeDownloadViewsVisibility(binding, GONE)
                callShowingViewsFunction(binding, redactionViewModel)
            }
        })

        return binding.root
    }

    private fun showViewsForCreation(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        binding.tNameForRedaction.visibility = VISIBLE
        binding.tDesriptionRedaction.visibility = VISIBLE
        binding.okNameButton.visibility = VISIBLE
        binding.okDescriptionButton.visibility = VISIBLE
        binding.completeRedactionButton.visibility = VISIBLE
        binding.rCancleButton.visibility = VISIBLE
        showWeeksViews(binding, viewModel)
        showOffDaysImages(viewModel)
        showDaysButtons(viewModel)
    }

    private fun showViewsForDeleting(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        binding.confirmText.text = viewModel.setConfirmText()
        binding.confirmText.visibility = VISIBLE
        binding.rCancleButton.visibility = VISIBLE
        binding.rDeleteTemplateButton.visibility = VISIBLE
    }

    private fun callShowingViewsFunction(
        binding: FragmentRedactionBinding,
        viewModel: RedactionViewModel
    ) {
        viewModel.setTextForScreen()
        setTemplateNameAndDescriptionText(binding, viewModel)
        showOnRequiredViews(binding)
        showWeeksViews(binding, viewModel)
        showOffDaysImages(viewModel)
        showDaysButtons(viewModel)
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

    private fun setClickListenerForDaysImages() {
        daysImageMap.forEach { (key, value) ->
            value.setOnClickListener {
                changeButtonVisibility(key)
            }
        }
    }

    private fun changeButtonVisibility(key: Int) {
        daysButtonMap[key]?.visibility = VISIBLE
        daysImageMap[key]?.visibility = GONE
    }

    private fun showOnRequiredViews(binding: FragmentRedactionBinding) {
        binding.completeRedactionButton.visibility = VISIBLE
        binding.tDescriptonText.visibility = VISIBLE
        binding.hintDescriptonTextView.visibility = VISIBLE
        binding.rCancleButton.visibility = VISIBLE
    }

    private fun changeDownloadViewsVisibility(binding: FragmentRedactionBinding, visibility: Int) {
        binding.downloadProgres.visibility = visibility
        binding.downloadText.visibility = visibility
    }

    private fun showWeeksViews(binding: FragmentRedactionBinding, viewModel: RedactionViewModel) {
        binding.firstWeekRedaction.isVisible = viewModel.showingWeek(1)
        binding.firstWeekTextView.isVisible = viewModel.showingWeek(1)
        binding.deleteFirstWeekImage.isVisible = viewModel.showingWeek(1)
        binding.addFirstWeekImage.isVisible = !viewModel.showingWeek(1)

        binding.secondWeekRedaction.isVisible = viewModel.showingWeek(2)
        binding.secondWeekTextView.isVisible = viewModel.showingWeek(2)
        binding.deleteSecondWeekImage.isVisible = viewModel.showingWeek(2)
        binding.addSecondWeekImage.isVisible =
            GONE == binding.secondWeekRedaction.visibility && VISIBLE == binding.firstWeekRedaction.visibility

        binding.thirdWeekRedction.isVisible = viewModel.showingWeek(3)
        binding.thirdWeekTextView.isVisible = viewModel.showingWeek(3)
        binding.deleteThirdWeekImage.isVisible = viewModel.showingWeek(3)
        binding.addThirdWeekImage.isVisible =
            GONE == binding.thirdWeekRedction.visibility && VISIBLE == binding.secondWeekRedaction.visibility

        binding.fourthWeekRedaction.isVisible = viewModel.showingWeek(4)
        binding.fourthWeekTextView.isVisible = viewModel.showingWeek(4)
        binding.deleteFourthWeekImage.isVisible = viewModel.showingWeek(4)
        binding.addFourthWeekImage.isVisible =
            GONE == binding.fourthWeekRedaction.visibility && VISIBLE == binding.thirdWeekRedction.visibility
    }


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

    private fun setClickListenerForDaysButtons(viewModel: RedactionViewModel) {
        daysButtonMap.forEach { (key, value) ->
            value.setOnClickListener {
                viewModel.createTrainingDay(Util.getWeekNumber(key), Util.getDayNumber(key))
                findNavController().navigate(R.id.action_redactionFragment_to_exerciseListFragment)
            }
        }
    }

    private fun showOnWeeks(weekNumber: Int, binding: FragmentRedactionBinding) {
        when (weekNumber) {
            1 -> {
                binding.addFirstWeekImage.visibility = GONE
                binding.addSecondWeekImage.isVisible =
                    GONE == binding.secondWeekRedaction.visibility
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
                binding.addFourthWeekImage.isVisible =
                    GONE == binding.fourthWeekRedaction.visibility
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
                binding.addFirstWeekImage.visibility = VISIBLE
            }
            2 -> {
                binding.secondWeekRedaction.visibility = GONE
                binding.secondWeekTextView.visibility = GONE
                binding.deleteSecondWeekImage.visibility = GONE
                binding.addSecondWeekImage.visibility = VISIBLE
            }
            3 -> {
                binding.thirdWeekRedction.visibility = GONE
                binding.thirdWeekTextView.visibility = GONE
                binding.deleteThirdWeekImage.visibility = GONE
                binding.addThirdWeekImage.visibility = VISIBLE
            }
            4 -> {
                binding.fourthWeekRedaction.visibility = GONE
                binding.fourthWeekTextView.visibility = GONE
                binding.deleteFourthWeekImage.visibility = GONE
                binding.addFourthWeekImage.visibility = VISIBLE
            }
        }
    }


    private fun showDaysButtons(viewModel: RedactionViewModel) {
        daysButtonMap.map {
            it.value.isVisible =
                viewModel.checkExistDays(Util.getWeekNumber(it.key), Util.getDayNumber(it.key))
        }
    }

    private fun showOffDaysImages(viewModel: RedactionViewModel) {
        daysImageMap.map {
            it.value.isVisible =
                !viewModel.checkExistDays(Util.getWeekNumber(it.key), Util.getDayNumber(it.key))
        }
    }

}