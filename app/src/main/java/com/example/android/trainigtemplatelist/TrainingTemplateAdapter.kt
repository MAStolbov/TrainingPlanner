package com.example.android.trainigtemplatelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.trainingplanner.databinding.ListItemTrainingTemplateBinding



class TrainingTemplateAdapter : ListAdapter<TrainingTemplate, TrainingTemplateAdapter.TemplateViewHolder>(TrainingTemplateDiffCCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateViewHolder {
        return TemplateViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: TemplateViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(clickListenerForRedactionButton(item.templateId),clickListenerForDeleteTemplateImView(item.templateId,item.templateName),item)
        }

    }

    private fun clickListenerForRedactionButton(templateId: Long): View.OnClickListener {
        return View.OnClickListener {
            it.findNavController().navigate(TrainingTemplatesListFragmentDirections.actionTrainingTemplatesListFragmentToRedactionFragment(templateId,"Redaction"))
        }
    }

    private fun clickListenerForDeleteTemplateImView(templateId:Long,templateName:String):View.OnClickListener{
        return View.OnClickListener {
            it.findNavController().navigate(TrainingTemplatesListFragmentDirections.actionTrainingTemplatesListFragmentToRedactionFragment(templateId,"Delete",templateName))
        }
    }


    class TemplateViewHolder private constructor(val binding: ListItemTrainingTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listenerForRedactionButton: View.OnClickListener, listenerForDeleteTemplate:View.OnClickListener, item: TrainingTemplate) {
            binding.clickListenerForRedactionButton = listenerForRedactionButton
            binding.clickListenerForDeleteTemplateImView = listenerForDeleteTemplate
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