//package com.example.projetutstodolist05
//
//import android.app.DatePickerDialog
//import android.app.Dialog
//import android.app.TimePickerDialog
//import android.content.Context
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import java.util.*
//
//class DialogAddTodoList(private val context: Context) : Dialog(context) {
//
//    private lateinit var etTopic: EditText
//    private lateinit var etDescription: EditText
//    private lateinit var btnAdd: Button
//    private lateinit var btnCancel: Button
//    private lateinit var tvDate: TextView
//    private lateinit var tvTime: TextView
//
//    private var onAddListener: OnAddListener? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.dialog_add_todo_list)
//
//        etTopic = findViewById(R.id.todo_topic)
//        etDescription = findViewById(R.id.todo_description)
//        btnAdd = findViewById(R.id.dialog_add)
//        btnCancel = findViewById(R.id.dialog_cancel)
//        tvDate = findViewById(R.id.todo_date_text)
//        tvTime = findViewById(R.id.todo_time_text)
//
//        btnAdd.setOnClickListener {
//            val id: Int =1 ;
//            val topic = etTopic.text.toString()
//            val description = etDescription.text.toString()
//            val date = tvDate.text.toString()
//            val time = tvTime.text.toString()
//
//            if (topic.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
//                onAddListener?.onAdd(Todo(
//                    id = id,
//                    topic = topic,
//                    description = description,
//                    date = date,
//                    time = time
//                ))
//                dismiss()
//            } else {
//                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        btnCancel.setOnClickListener {
//            dismiss()
//        }
//
//        tvDate.setOnClickListener {
//            showDatePicker()
//        }
//
//        tvTime.setOnClickListener {
//            showTimePicker()
//        }
//    }
//
//    private fun showDatePicker() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePickerDialog = DatePickerDialog(
//            context,
//            { _, year, month, dayOfMonth ->
//                val selectedDate = "$dayOfMonth/${month + 1}/$year"
//                tvDate.text = selectedDate
//            },
//            year, month, dayOfMonth
//        )
//
//        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
//        datePickerDialog.show()
//    }
//
//    private fun showTimePicker() {
//        val calendar = Calendar.getInstance()
//        val hour = calendar.get(Calendar.HOUR_OF_DAY)
//        val minute = calendar.get(Calendar.MINUTE)
//
//        val timePickerDialog = TimePickerDialog(
//            context,
//            { _, hourOfDay, minute ->
//                val selectedTime = "${hourOfDay.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
//                tvTime.text = selectedTime
//            },
//            hour, minute, true
//        )
//
//        timePickerDialog.show()
//    }
//
//    fun setOnAddListener(listener: OnAddListener) {
//        this.onAddListener = listener
//    }
//
//    interface OnAddListener {
//        fun onAdd(todo: Todo)
//    }
//}
//
