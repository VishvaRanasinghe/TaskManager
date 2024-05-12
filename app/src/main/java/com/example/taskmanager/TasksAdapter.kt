package com.example.taskmanager

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(
    private var tasks: List<Task>,
    private val context: Context
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val db: TaskDatabaseHelper = TaskDatabaseHelper(context)

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val readButton: ImageView = itemView.findViewById(R.id.readButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.titleTextView.text = task.title
        holder.contentTextView.text = task.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java).apply {
                putExtra("task_id", task.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Delete Task")
            builder.setMessage("Are you sure you want to delete this Task?")
            builder.setPositiveButton("Yes") { _, _ ->
                db.deleteTask(task.id) // Deleting the task using TaskDatabaseHelper instance
                refreshData(db.getAllTasks()) // Refreshing the adapter data after deletion
                Toast.makeText(holder.itemView.context, "Task Deleted", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { _, _ -> }
            val dialog = builder.create()
            dialog.show()
        }

        holder.readButton.setOnClickListener {
            // Handle the read button click here
            val intent = Intent(holder.itemView.context, ReadingTasksActivity::class.java).apply {
                putExtra("task_id", task.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    fun refreshData(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
