package com.example.projetutstodolist05

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class HistoryAdapter(
    private val context: Context,
    private val historyList: List<History>) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        val history = historyList[position]

        holder.tvTopic.text = history.topic
        holder.tvDescription.text = history.description
        holder.tvCreatedDate.text = history.date
        holder.tvCreatedTime.text = history.time
        holder.tvCompletedDate.text = history.completedDate
        holder.tvStatus.text = history.status

        return view!!
    }

    override fun getItem(position: Int): History {
        return historyList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return historyList.size
    }

    private class ViewHolder(view: View) {
        val tvTopic: TextView = view.findViewById(R.id.tv_topic)
        val tvDescription: TextView = view.findViewById(R.id.tv_desc_history)
        val tvCreatedDate: TextView = view.findViewById(R.id.tv_date_history)
        val tvCreatedTime: TextView = view.findViewById(R.id.tv_time_history)
        val tvCompletedDate: TextView = view.findViewById(R.id.tv_time_history)
        val tvStatus: TextView = view.findViewById(R.id.tv_status_history)
    }
}
