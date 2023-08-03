package com.example.appc06_sql

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ProgramStudiAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ProgramStudi, ProgramStudiAdapter.ProgramStudiViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramStudiViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemProgramStudiBinding.inflate(layoutInflater, parent, false)
        return ProgramStudiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramStudiViewHolder, position: Int) {
        val programStudi = getItem(position)
        holder.bind(programStudi)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(programStudi)
        }
    }

    class ProgramStudiViewHolder(private val binding: ListItemProgramStudiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(programStudi: ProgramStudi) {
            binding.programStudi = programStudi
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProgramStudi>() {
        override fun areItemsTheSame(oldItem: ProgramStudi, newItem: ProgramStudi): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProgramStudi, newItem: ProgramStudi): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (programStudi: ProgramStudi) -> Unit) {
        fun onClick(programStudi: ProgramStudi) = clickListener(programStudi)
    }
}
