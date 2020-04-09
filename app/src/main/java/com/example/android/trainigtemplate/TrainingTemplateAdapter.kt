package com.example.android.trainigtemplate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.trainingplanner.R
import com.example.android.trainingplanner.databinding.ListItemTrainingTemplateBinding
import com.example.android.util.TemporaryDataStorageClass


class TrainingTemplateAdapter :
    ListAdapter<TrainingTemplate, TrainingTemplateAdapter.TemplateViewHolder>(
        TrainingTemplateDiffCCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateViewHolder {
        return TemplateViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: TemplateViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(createOnClickListener(item.templateId),item)
        }

    }

    private fun createOnClickListener(templateId: Long): View.OnClickListener {
        return View.OnClickListener {
            it.findNavController().navigate(TrainingTemplatesListFragmentDirections.actionTrainingTemplatesListFragmentToRedactionFragment(templateId))
        }
    }


    class TemplateViewHolder private constructor(val binding: ListItemTrainingTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: TrainingTemplate) {
            val res = itemView.context.resources
            binding.clickListener = listener
            binding.templateName.text = "Name: ${item.templateName} "
            binding.templateId.text = "ID: ${item.templateId}"
            binding.description.text = "Description: ${item.templateDescription}"
            binding.weeksNumber.text = "Weeks: ${item.numberOfTrainingWeeks}"
        }

        companion object {
            fun from(parent: ViewGroup): TemplateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTrainingTemplateBinding.inflate(layoutInflater, parent, false)
                return TemplateViewHolder(binding)
            }
        }

    }
}

class TrainingTemplateDiffCCallback : DiffUtil.ItemCallback<TrainingTemplate>() {
    override fun areItemsTheSame(oldItem: TrainingTemplate, newItem: TrainingTemplate): Boolean {
        return oldItem.templateId == newItem.templateId
    }

    override fun areContentsTheSame(oldItem: TrainingTemplate, newItem: TrainingTemplate): Boolean {
        return oldItem == newItem
    }

}