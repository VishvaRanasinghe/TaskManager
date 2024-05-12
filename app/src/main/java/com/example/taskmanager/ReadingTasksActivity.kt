package com.example.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.taskmanager.databinding.ActivityReadingTasksBinding

class ReadingTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadingTasksBinding
    private lateinit var db: TaskDatabaseHelper

    private var taskID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingTasksBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = TaskDatabaseHelper(this)

        taskID = intent.getIntExtra("task_id", -1)

        if (taskID == -1) {
            finish()
            return
        }

        val task = db.getTaskByID(taskID)

        binding.titlereadText.text = task.title
        binding.contentreadText.text = task.content

        binding.outButton.setOnClickListener {
            finish()
        }
    }
}
