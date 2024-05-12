package com.example.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmanager.databinding.ActivityAddTasksBinding

class AddTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTasksBinding
    private lateinit var db: TaskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditTitle.text.toString()

            // Check if title and content are not empty
            if (title.isNotEmpty() && content.isNotEmpty()) {
                val task = Task(0, title, content)
                db.insertTask(task)

                // Finish the activity and show a toast message to the user
                finish()
                Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
            } else {
                // Show a toast message to inform the user if title or content is empty
                Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
