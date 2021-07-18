package org.roadtocode.sampletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val taskList: MutableList<TaskItem>?) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(taskList!=null)
        {
            holder.tvTask.text = taskList.get(position).taskName
            holder.tvDescription.text = taskList.get(position).taskDescription
        }
    }

    override fun getItemCount(): Int {
        if (taskList != null) {
            return taskList.size
        }
        return 0
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTask: TextView = itemView.findViewById(R.id.tvTask)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }
}