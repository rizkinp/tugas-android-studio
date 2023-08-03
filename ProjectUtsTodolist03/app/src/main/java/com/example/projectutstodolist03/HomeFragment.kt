package com.example.projectutstodolist03

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.*

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat

class HomeFragment : Fragment() {

    private lateinit var databaseHandler: DBHelper
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize database handler
        databaseHandler = DBHelper(requireContext())

        // Initialize task list and adapter
        val taskList = mutableListOf<Task>()
        taskAdapter = TaskAdapter(requireContext(), taskList)

        // Initialize listview and set adapter
        val listView: ListView = view.findViewById(R.id.list_view)
        listView.adapter = taskAdapter

        // Get tasks from database and add to task list
        taskList.addAll(databaseHandler.getAllTasks())
        taskAdapter.notifyDataSetChanged()

        // Set on item click listener for listview
//        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            val selectedTask: Task = parent.getItemAtPosition(position) as Task
//
//            // Open dialog to mark task as completed
//            val builder = AlertDialog.Builder(requireContext())
//            builder.setMessage("Are you sure you want to mark this task as completed?")
//                .setCancelable(false)
//                .setPositiveButton("Yes") { dialog, which ->
//                    // Set task as completed in database and remove from listview
////                    databaseHandler.completeTask(selectedTask)
//                    taskList.remove(selectedTask)
//                    taskAdapter.notifyDataSetChanged()
//
//                    // Add completed task to history
////                    val historyTask = HistoryTask(
////                        selectedTask.topic,
////                        selectedTask.description,
////                        selectedTask.date,
////                        selectedTask.time,
////                        Calendar.getInstance().time
////                    )
////                    databaseHandler.addHistoryTask(historyTask)
//
//                    Toast.makeText(
//                        requireContext(),
//                        "Task completed!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                .setNegativeButton("No") { dialog, which -> }
//            builder.create().show()
//        }

        // Set on item long click listener for listview
//        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
//            val selectedTask: Task = parent.getItemAtPosition(position) as Task
//
//            // Open dialog to delete task
//            val builder = AlertDialog.Builder(requireContext())
//            builder.setMessage("Are you sure you want to delete this task?")
//                .setCancelable(false)
//                .setPositiveButton("Yes") { dialog, which ->
//                    // Delete task from database and listview
//                    databaseHandler.(selectedTask)
//                    taskList.remove(selectedTask)
//                    taskAdapter.notifyDataSetChanged()
//
//                    Toast.makeText(
//                        requireContext(),
//                        "Task deleted!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                .setNegativeButton("No") { dialog, which -> }
//            builder.create().show()
//
//            true
//        }

        // Initialize date picker dialog
        val dateEditText: EditText = view.findViewById(R.id.date_edit_text)
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val formattedDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar.time)
            dateEditText.setText(formattedDate)
        }
        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        // Initialize time picker dialog
        val timeEditText: EditText = view.findViewById(R.id.time_edit_text)
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            val formattedTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
            timeEditText.setText(formattedTime)
        }
        timeEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
        }

        // Set on click listener for add task button
        val addTaskButton: Button = view.findViewById(R.id.add_button)
        addTaskButton.setOnClickListener {
            val topicEditText: EditText = view.findViewById(R.id.topic_edit_text)
            val descriptionEditText: EditText = view.findViewById(R.id.description_edit_text)

            val topic = topicEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val date = dateEditText.text.toString().trim()
            val time = timeEditText.text.toString().trim()

            // Validate inputs
            if (topic.isEmpty()) {
                topicEditText.error = "Please enter a topic"
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                descriptionEditText.error = "Please enter a description"
                return@setOnClickListener
            }

            if (date.isEmpty()) {
                Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (time.isEmpty()) {
                Toast.makeText(requireContext(), "Please select a time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add task to database and listview
            val task = Task(topic.toInt(), description, date, time)
            databaseHandler.addTask(task)
            taskList.add(task)
            taskAdapter.notifyDataSetChanged()
            // Clear input fields
            topicEditText.setText("")
            descriptionEditText.setText("")
            dateEditText.setText("")
            timeEditText.setText("")

            Toast.makeText(requireContext(), "Task added!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}

