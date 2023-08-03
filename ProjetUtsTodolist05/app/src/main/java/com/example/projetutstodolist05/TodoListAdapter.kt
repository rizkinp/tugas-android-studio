package com.example.projetutstodolist05

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TodoListAdapter(
    private val context: Context,
    private var todoList: ArrayList<Todo>
) : BaseAdapter() {

    override fun getCount(): Int {
        return todoList.size
    }


    override fun getItem(position: Int): Todo {
        return todoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val todo = getItem(position) as Todo

        holder.tvTopic.text = todo.topic
        holder.tvDescription.text = todo.description
        holder.tvDate.text = todo.date
        holder.tvTime.text = todo.time

        return view!!
    }

    private class ViewHolder(view: View) {
        val tvTopic: TextView = view.findViewById(R.id.tv_topic)
        val tvDescription: TextView = view.findViewById(R.id.tv_description)
        val tvDate: TextView = view.findViewById(R.id.tv_date)
        val tvTime: TextView = view.findViewById(R.id.tv_time)
    }

    fun updateTodoList(todoList: MutableList<Todo>) {
        this.todoList = todoList as ArrayList<Todo>
        notifyDataSetChanged()
    }
}

