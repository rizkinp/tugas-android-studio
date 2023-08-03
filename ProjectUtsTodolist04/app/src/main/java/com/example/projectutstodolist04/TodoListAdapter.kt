package com.example.projectutstodolist04

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Context
import android.widget.BaseAdapter


class TodoListAdapter(private val context: Context, private val todoList: ArrayList<Todo>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(android.R.layout.simple_list_item_2, null)
        }

        val todo = getItem(position) as Todo

        val topicTextView = view?.findViewById<TextView>(android.R.id.text1)
        topicTextView?.text = todo.topic

        val descriptionTextView = view?.findViewById<TextView>(android.R.id.text2)
        descriptionTextView?.text = todo.description

        return view!!
    }

    override fun getItem(position: Int): Any {
        return todoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return todoList.size
    }
}

