package com.example.projectutstodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private lateinit var dbHandler: TodoDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHandler = DatabaseHandler(requireContext())

        btnAddTask.setOnClickListener {
            saveTask()
        }

        loadTasks()
    }

    private fun saveTask() {
        val task = etTask.text.toString().trim()
        val date = tvDate.text.toString().trim()
        val time = tvTime.text.toString().trim()

        if (task.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val todo = Todo(task = task, date = date, time = time, status = 0)

        if (dbHandler.addTask(todo) > -1) {
            Toast.makeText(requireContext(), "Task saved successfully", Toast.LENGTH_SHORT).show()
            etTask.text?.clear()
            tvDate.text = "Select Date"
            tvTime.text = "Select Time"
            loadTasks()
        } else {
            Toast.makeText(requireContext(), "Unable to save task", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadTasks() {
        val tasks = dbHandler.getAllTodosByUserId()

        if (tasks.isNotEmpty()) {
            rvTasks.visibility = View.VISIBLE
            tvNoTasks.visibility = View.GONE

            rvTasks.layoutManager = LinearLayoutManager(requireContext())
            rvTasks.adapter = TaskAdapter(requireContext(), tasks)
        } else {
            rvTasks.visibility = View.GONE
            tvNoTasks.visibility = View.VISIBLE
        }
    }
}
