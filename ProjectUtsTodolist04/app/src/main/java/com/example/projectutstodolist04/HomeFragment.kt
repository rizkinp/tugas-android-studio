package com.example.projectutstodolist04

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var greetingTextView: TextView
    private lateinit var todoListRecyclerView: RecyclerView

    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var todoListViewModel: TodoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi view
        greetingTextView = view.findViewById(R.id.greeting_text_view)
        todoListRecyclerView = view.findViewById(R.id.todo_list_recycler_view)

        // Set adapter dan layout manager pada RecyclerView
        todoListAdapter = TodoListAdapter()
        todoListRecyclerView.adapter = todoListAdapter
        todoListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi ViewModel
        todoListViewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set greeting text
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        greetingTextView.text = when (currentHour) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            else -> "Good Night"
        }

        // Observasi perubahan pada list todo
        todoListViewModel.todoList.observe(viewLifecycleOwner, { todoList ->
            todoListAdapter.submitList(todoList)
        })
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}


