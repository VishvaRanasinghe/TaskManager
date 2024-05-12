package com.example.taskmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager.databinding.ActivityUpdateTaskBinding

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: TaskDatabaseHelper
    private var taskID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        taskID = intent.getIntExtra("task_id", -1)

        if (taskID == -1) {
            finish()
            return
        }

        val task = db.getTaskByID(taskID)
        binding.updateTitleEditText.setText(task.title)
        binding.updateContentEditTitle.setText(task.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString().trim()
            val newContent = binding.updateContentEditTitle.text.toString().trim()

            if (newTitle.isNotEmpty() && newContent.isNotEmpty()) {
                val updatedTask = Task(taskID, newTitle, newContent)
                db.updateTask(updatedTask)
                finish()

                Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            } else {
                //
                Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
