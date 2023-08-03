package com.example.projekuts_final

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var fab: FloatingActionButton
    private lateinit var dbHelper: DatabaseHandler
    private lateinit var todoListAdapter: TodoListAdapter
//    private lateinit var greeting: TextView
//    private lateinit var historyListAdapter: HistoryAdapter
//    private lateinit var historyListAdapter: HistoryAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        listView = view.findViewById(R.id.todo_listview)
        fab = view.findViewById(R.id.fab_add_todo)
//        greeting = view.findViewById(R.id.greeting_textview)


        // Inisialisasi DatabaseHelper
        dbHelper = DatabaseHandler(requireContext())

        // Inisialisasi TodoListAdapter
        todoListAdapter = TodoListAdapter(requireContext(), dbHelper.getAllTodoList())

        // Set adapter untuk listView
        listView.adapter = todoListAdapter

        //tambahan
        // set up list view for history
//        val view_his = inflater.inflate(R.layout.fragment_history, container, false)
//        val historyListView = view_his.findViewById<ListView>(R.id.lv_history)
//        historyListAdapter = HistoryAdapter(requireContext(), dbHelper.getAllHistory())
//        historyListView.adapter = historyListAdapter
        val username: String? = arguments?.getString("USERNAME_KEY")

//        greeting.setText(username)



        listView.setOnItemClickListener { parent, view, position, id ->
//            val selectedItem = historyListAdapter.getItem(position)
            val todo = todoListAdapter.getItem(position)

//            val selectedItem = historyListView.getItemAtPosition(position) as Todo
//            dbHelper.deleteTodoById(selectedItem.id)


            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setMessage("Apakah tugas telah selesai?")
            alertDialogBuilder.setPositiveButton("Ya") { dialog, _ ->

//                val todo = adapter.getItem(position)
//                dbHelper.deleteTodoById(todo)

                val historyItem = History(
                    id = 0,
                    todo.topic,
                    todo.description,
                    todo.date,
                    todo.time,
                    LocalDate.now().toString(),
                    "completed"
                )
                dbHelper.addHistory(historyItem)

//                adapter.updateTodoList(dbHelper.getTodoList())

//                val todoItem = todoListAdapter.getItem([position])
//                databaseHandler.deleteData(todoItem)


                // Insert data into database
                // Get the current date and time
                // Get the current date and time
//                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//                val currentTime: String = dateFormat.format(Date())

                // Insert the selected item and the current time to the history table using the dbHelper instance

                // Insert the selected item and the current time to the history table using the dbHelper instance


//                dbHelper.addHistory(selectedItem.id)

                // Insert data into database
//                dbHelper.addHistory(calculation)

//                val selectedTodo = todoListAdapter.getItem(position)
//                addHistory(selectedTodo)
                dbHelper.deleteTodoById(todo.id)
                todoListAdapter.updateTodoList(dbHelper.getAllTodoList())
                todoListAdapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Tugas telah selesai", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }


        // Set onClickListener untuk fab
        fab.setOnClickListener {
            showAddTodoListDialog()
        }
        return view
    }

    // Menampilkan dialog untuk menambahkan TodoList
    private fun showAddTodoListDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_todo_list, null)
        dialogBuilder.setView(dialogView)

        val etTopic = dialogView.findViewById<EditText>(R.id.todo_topic)
        val etDescription = dialogView.findViewById<EditText>(R.id.todo_description)
        val etDate = dialogView.findViewById<Button>(R.id.date_picker)
        val etTime = dialogView.findViewById<Button>(R.id.time_picker)

        // Set onClickListener untuk etDate
        etDate.setOnClickListener {
            showDatePickerDialog(etDate)
        }

        // Set onClickListener untuk etTime
        etTime.setOnClickListener {
            showTimePickerDialog(etTime)
        }

        dialogBuilder.setPositiveButton("Tambah") { _, _ ->
            val topic = etTopic.text.toString()
            val description = etDescription.text.toString()
            val date = etDate.text.toString()
            val time = etTime.text.toString()

            // Insert data ke database
////            dbHelper.addTodo(Todo(id,topic, description, date, time))
//            dbHelper.addTodoList(Todo(id, topic, description, date, time))
            // Insert data ke database
            val id = dbHelper.getNextTodoId()
            dbHelper.addTodo(Todo(id, topic, description, date, time, "", ""))

            // Update adapter dan notifyDataSetChanged
            todoListAdapter.updateTodoList(dbHelper.getAllTodoList())
            todoListAdapter.notifyDataSetChanged()
        }

        dialogBuilder.setNegativeButton("Cancel") { _, _ -> }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    // Menampilkan DatePickerDialog untuk memilih tanggal
    private fun showDatePickerDialog(etDate: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val newMonth = selectedMonth + 1

                val formattedDate = "$selectedYear/$newMonth/$selectedDay"
                etDate.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    // Menampilkan TimePickerDialog untuk memilih waktu
    private fun showTimePickerDialog(etTime: Button) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val formattedTime = "${String.format("%02d", selectedHour)}:${String.format("%02d", selectedMinute)}"
                etTime.setText(formattedTime)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }


}