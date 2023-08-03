package com.example.projectutstodolist03

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TaskAdapter(context: Context, private val tasks: MutableList<Task>) : BaseAdapter() {
    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(position: Int): Any {
        return tasks[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_task, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val task = getItem(position) as Task

        holder.topicTextView.text = task.topic
        holder.descriptionTextView.text = task.description
        holder.dateTimeTextView.text = "${task.date} ${task.time}"

        return view
    }

    private class ViewHolder(view: View) {
        val topicTextView: TextView = view.findViewById(R.id.topic_text_view)
        val descriptionTextView: TextView = view.findViewById(R.id.description_text_view)
        val dateTimeTextView: TextView = view.findViewById(R.id.date_text_view)
    }
}
