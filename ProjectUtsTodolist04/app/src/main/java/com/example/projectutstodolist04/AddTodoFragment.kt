package com.example.projectutstodolist04

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddTodoFragment : Fragment() {
    private lateinit var db: DBHelper
    private lateinit var topicEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText:EditText
    private lateinit var timeEditText: EditText


    class AddTodoFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_add_todo, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            add_button.setOnClickListener {
                val topic = topic_edit_text.text.toString()
                val description = description_edit_text.text.toString()
                val date =
                    date_picker.year.toString() + "-" + (date_picker.month + 1).toString() + "-" + date_picker.dayOfMonth.toString()
                val time = time_picker.hour.toString() + ":" + time_picker.minute.toString()

                val todo = Todo(
                    id = 0,
                    topic = topic,
                    description = description,
                    date = date,
                    time = time
                )

                val todoListHelper = TodoListHelper(requireContext())
                todoListHelper.addData(todo)
                topic_edit_text.setText("")
                description_edit_text.setText("")

                // TODO: Show toast or dialog to inform user that the todo has been added

                // Move back to home fragment
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        private fun clearForm() {
            topicEditText.setText("")
            descriptionEditText.setText("")
            dateEditText.setText("")
            timeEditText.setText("")
        }
    }
}
