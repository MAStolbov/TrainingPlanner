package com.example.android.trainigtemplate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.database.templateEntityDao.TrainingTemplate
import com.example.android.trainingplanner.R


class TrainingTemplateAdapter :RecyclerView.Adapter<TrainingTemplateAdapter.TemplateViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemplateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_training_template,parent,false)
        return TemplateViewHolder(view)
    }

    var data = listOf<TrainingTemplate>()

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TemplateViewHolder, position: Int){
        val item = data[position]
        holder.templateName.text = item.templateName
    }


    class TemplateViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val templateName:TextView = itemView.findViewById(R.id.templateName)
    }
}