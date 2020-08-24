package com.example.android.exerciselist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.database.exerciseEntityDao.Exercise
import com.example.android.trainingplanner.databinding.ListItemExerciseBinding

class ExerciseAdapter :
    ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(
                moveFromExerciseListToExerciseDescription(
                    item.exerciseName,
                    item.dayNumber,
                    item.weekNumber
                ), item
            )
        }
    }

    private fun moveFromExerciseListToExerciseDescription(
        exerciseName: String, exerciseDayNumber: Int, exerciseWeekNumber: Int
    ): View.OnClickListener {
        return View.OnClickListener {
            it.findNavController().navigate(
                ExerciseListFragmentDirections.actionExerciseListFragmentToCreatingExerciseFragment(
                    exerciseDayNumber, exerciseWeekNumber, "redaction", exerciseName
                )
            )
        }
    }

    class ExerciseViewHolder private constructor(val binding: ListItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListenerForExerciseElement: View.OnClickListener, item: Exercise) {
            binding.clickListenerForExerciseElement = clickListenerForExerciseElement
            binding.exerciseName.text = item.exerciseName
            binding.setNumber.text = "Sets: ${item.set}"
            binding.repNumber.text = "Reps: ${item.rep}"
            binding.weight.text = "Weight: ${item.weight} kg"
        }

        companion object {
            fun from(parent: ViewGroup): ExerciseViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemExerciseBinding.inflate(layoutInflater, parent, false)
                return ExerciseViewHolder(binding)
            }
        }
    }
}

class ExerciseDiffCCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.exerciseId == newItem.exerciseId
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem == newItem
    }

}