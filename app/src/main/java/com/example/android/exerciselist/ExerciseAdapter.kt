package com.example.android.exerciselist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.trainingplanner.R

class ExerciseAdapter:RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_exercise, parent, false)
        return ExerciseViewHolder(
            view
        )
    }

    var data = listOf<Exercise>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        holder.exerciseName.text = item.exerciseName
        holder.setNumber.text = "Sets: ${item.set}"
        holder.repNumber.text = "Reps: ${item.rep}"
        holder.weight.text = "Weight: ${item.weight} kg"
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val exerciseName:TextView = itemView.findViewById(R.id.exerciseName)
        val setNumber: TextView = itemView.findViewById(R.id.setNumber)
        val repNumber:TextView = itemView.findViewById(R.id.repNumber)
        val weight:TextView = itemView.findViewById(R.id.weight)
    }
}